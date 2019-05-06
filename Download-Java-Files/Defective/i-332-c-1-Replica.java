package ds.replica;

import ds.client.RequestParameters;
import ds.core.StubLoader;
import ds.core.Timestamp;
import ds.frontend.MutationResponse;
import ds.frontend.QueryResponse;
import ds.frontend.Request;
import ds.replica.updatelog.UpdateLog;
import ds.replica.updatelog.UpdateLogEntry;

import java.io.NotActiveException;
import java.rmi.RemoteException;
import java.util.*;

import static ds.core.NetworkSimulator.NUMBER_OF_REPLICAS;

public class Replica extends TimerTask implements ReplicaApi {
    private int replicaId;

    private UpdateLog updateLog = new UpdateLog();

    private Timestamp replicaTimestamp = new Timestamp(NUMBER_OF_REPLICAS);

    private ReplicaStatus status = ReplicaStatus.ACTIVE;

    private Timestamp valueTimestamp = new Timestamp(NUMBER_OF_REPLICAS);

    private ReplicaValue value = new ReplicaValue();

    private StubLoader stubLoader;

    private Set<UUID> executedOperations = new HashSet<>();

    private List<Timestamp> timestampTable = new ArrayList<>();

    @Override
    public ReplicaStatus requestStatus() throws RemoteException {
        return status;
    }

    @Override
    public void setReplicaStatus(ReplicaStatus status) throws RemoteException {
        this.status = status;
    }

    private void mergeUpdateLog(List<UpdateLogEntry> entries) {
        entries.stream()
                .filter(entry -> !entry.getUpdateTimestamp().isBeforeOrEqual(replicaTimestamp))
                .forEach(updateLog::add);
    }

    private void updateTimestamps(GossipMessage message) {
        replicaTimestamp.merge(message.getReplicaTimestamp());

        timestampTable.set(replicaId, replicaTimestamp);
        timestampTable.set(message.getSenderId(), message.getReplicaTimestamp());
    }

    private void clearUpdateLog() {
        updateLog.removeOutdatedEntries(timestampTable);
    }

    @Override
    public void processGossipMessage(GossipMessage message) throws RemoteException {
//        System.out.printf("Replica %d got gossip from %d of size %d\n", replicaId, message.getSenderId(), message.getUpdateLogEntries().size());
        mergeUpdateLog(message.getUpdateLogEntries());
        updateTimestamps(message);

        while (updateLog.hasStableEntry(valueTimestamp) && executeStableUpdates());

        clearUpdateLog();
    }

    @Override
    public List<UpdateLogEntry> findAllRequiredUpdates(Timestamp timestamp) throws RemoteException {
        return updateLog.anyEntryThat(entry ->
                entry.getUpdateTimestamp().get(replicaId) >= timestamp.get(replicaId));
    }

    private boolean haveProcessedRequest(Request request) {
        return executedOperations.contains(request.getUid());
    }

    private boolean executeEntry(UpdateLogEntry entry) {
//        System.out.printf("Replica %d is executing a request\n", replicaId);
        if (haveProcessedRequest(entry.getUpdateRequest())) {
            return false;
        }

        Request updateRequest = entry.getUpdateRequest();

        value.setRanking(updateRequest.getParameters());
        valueTimestamp.merge(entry.getUpdateTimestamp());
        executedOperations.add(updateRequest.getUid());

        return true;
    }

    private boolean executeStableUpdates() {
        return updateLog.getStableEntries(valueTimestamp).stream()
                .anyMatch(this::executeEntry);
    }

    private void catchupValueWithReplica(int replicaId) throws NotActiveException, RemoteException {
        ReplicaApi replica = stubLoader.getReplicaStub(replicaId);

        List<UpdateLogEntry> entries = replica.findAllRequiredUpdates(valueTimestamp);

        entries.forEach(updateLog::add);
    }

    private void catchupValue(Timestamp requiredTimestamp) throws RemoteException, NotActiveException {
        for (int i = 0; i < valueTimestamp.getDimension(); ++i) {
            if (valueTimestamp.get(i) < requiredTimestamp.get(i)) {
               catchupValueWithReplica(i);
            }
        }

        executeStableUpdates();
    }

    private QueryResponse performQueryRequest(Request request) {
        return new QueryResponse(valueTimestamp, value.getDetailsForMovie(request.getParameters()));
    }

    @Override
    public QueryResponse query(Request request) throws RemoteException {
        if (request.getTimestamp().isAfter(valueTimestamp)) {
            try {
                catchupValue(request.getTimestamp());
            } catch (NotActiveException e) {
                System.out.println("Failed to contact the replica who had our required updates\n" +
                        "in the real world we would ask other replicas for the other updates\n" +
                        "however I feel this is unlikely in our simulations and perhaps out of the scope of this course");
            }
        }

        return performQueryRequest(request);
    }

    private void incrementReplicaTimestamp() {
        replicaTimestamp.set(replicaId, replicaTimestamp.get(replicaId) + 1);
        timestampTable.set(replicaId, replicaTimestamp);
    }

    private MutationResponse scheduleMutationRequest(Request request) {
        if (haveProcessedRequest(request)) {
            return MutationResponse.wasFailure(request.getTimestamp());
        }

        incrementReplicaTimestamp();

        Timestamp newFrontendTimestamp = request.getTimestamp().copy();
        newFrontendTimestamp.set(replicaId, replicaTimestamp.get(replicaId));

        updateLog.add(replicaId, newFrontendTimestamp, request);

        executeStableUpdates();

        return MutationResponse.wasSuccessful(newFrontendTimestamp);
    }

    private boolean isUpdateRequestInvalid(Request request) {
        // can only update ratings that actually exist
        RequestParameters parameters = request.getParameters();

        return !value.hasUserRankedMovie(parameters.getUserId(), parameters.getMovieId());
    }

    @Override
    public MutationResponse update(Request request) throws RemoteException {
        if (isUpdateRequestInvalid(request)) {
            return MutationResponse.wasFailure(request.getTimestamp());
        }

        return scheduleMutationRequest(request);
    }

    @Override
    public MutationResponse submit(Request request) throws RemoteException {
        return scheduleMutationRequest(request);
    }

    public Replica(int replicaId) throws RemoteException {
        this.replicaId = replicaId;
        stubLoader = new StubLoader();

        for (int i = 0; i < NUMBER_OF_REPLICAS; ++i) {
            timestampTable.add(new Timestamp(NUMBER_OF_REPLICAS));
        }
    }

    private void sendGossipMessage(int replicaNumber, GossipMessage message) {
        try {
            stubLoader.getReplicaStub(replicaNumber).processGossipMessage(message);
        } catch (RemoteException | NotActiveException e) {
            // In real life, perhaps we would count the number of fails
            // and if it reached some threshold then we would then stop sending gossip messages to it.
        }
    }

    private void broadcastGossipMessages() {
        for (int replicaNumber = 0; replicaNumber < timestampTable.size(); ++replicaNumber) {
            if (replicaNumber == replicaId) { continue; }

            Timestamp estimatedTimestamp = timestampTable.get(replicaNumber);

            List<UpdateLogEntry> updatesReplicaNeeds = updateLog.anyEntryThat(
                    entry -> entry.getUpdateTimestamp().isAfter(estimatedTimestamp));

            sendGossipMessage(replicaNumber, new GossipMessage(replicaId, updatesReplicaNeeds, replicaTimestamp));
        }
    }

    @Override
    public void run() {
        broadcastGossipMessages();
    }
}
