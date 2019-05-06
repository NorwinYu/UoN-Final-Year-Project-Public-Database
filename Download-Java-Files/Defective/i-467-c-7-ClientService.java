package pl.put.boardgamemanager.person.client;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import pl.put.boardgamemanager.ValueDTO;
import pl.put.boardgamemanager.ListDTO;
import pl.put.boardgamemanager.Utils;
import pl.put.boardgamemanager.game.Game;
import pl.put.boardgamemanager.game.GameRepository;
import pl.put.boardgamemanager.game_copy.GameCopyRepository;
import pl.put.boardgamemanager.private_rental.PrivateRental;
import pl.put.boardgamemanager.private_rental.PrivateRentalDTO;
import pl.put.boardgamemanager.private_rental.PrivateRentalRepository;
import pl.put.boardgamemanager.private_reservation.PrivateReservation;
import pl.put.boardgamemanager.private_reservation.PrivateReservationRepository;
import pl.put.boardgamemanager.table.Table;
import pl.put.boardgamemanager.table.TableRepository;
import pl.put.boardgamemanager.tournament.Tournament;
import pl.put.boardgamemanager.tournament.TournamentRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameCopyRepository gameCopyRepository;

    @Autowired
    private PrivateReservationRepository privateReservationRepository;

    @Autowired
    private PrivateRentalRepository privateRentalRepository;

    @Autowired
    private TableRepository tableRepository;

    private List<Tournament> getNotParticipatedTournaments(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) return tournamentRepository.findAll();
        else {
            List<Tournament> participatedTournaments = client.getTournaments();
            List<Tournament> allTournaments = tournamentRepository.findAll();

            allTournaments.removeAll(participatedTournaments);

            return allTournaments;
        }
    }

    private ClientTournamentDTO tournamentToClientTournamentDTO(Tournament tournament) {
        Game game = gameRepository.findById(tournament.getGameId()).orElse(null);
        if (game == null) return null;
        else {
            ClientTournamentDTO dto = new ClientTournamentDTO();
            dto.setTournamentDTO(tournament.toDTO());
            dto.setGameName(game.getName());
            return dto;
        }
    }

    private ClientReservationDTO reservationToClientReservationDTO(PrivateReservation reservation) {
        Table table = tableRepository.findById(reservation.getTableId()).orElse(null);
        if (table == null) return null;
        else {
            ClientReservationDTO dto = new ClientReservationDTO();
            dto.setPrivateReservation(reservation);
            dto.setNumberOfSits(table.getNumberOfSits());
            return dto;
        }
    }

    public ClientDTO getClientDTOById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            ClientDTO dto = new ClientDTO();
            dto.setErrorMessage("There is no client with the given id");
            return dto;
        } else return client.toDTO();
    }

    public ClientDTO getClientDTOByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            ClientDTO dto = new ClientDTO();
            dto.setErrorMessage("There is no client with the given id");
            return dto;
        } else return client.toDTO();
    }

    public ValueDTO<Boolean> exists(Long id) {
        ValueDTO<Boolean> resultDTO = new ValueDTO<>();
        resultDTO.setValue(clientRepository.existsById(id));
        return resultDTO;
    }

    public ListDTO<ClientTournamentDTO> getParticipatedTournamentDTOs(Long id) {
        ListDTO<ClientTournamentDTO> resultDTO = new ListDTO<>();

        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            resultDTO.setValues(Collections.emptyList());
            resultDTO.setErrorMessage("There is no client with the given id");
        } else resultDTO.setValues(client.getTournaments().stream()
                .map(this::tournamentToClientTournamentDTO)
                .collect(Collectors.toList()));

        return resultDTO;
    }

    public ListDTO<ClientTournamentDTO> getAvailableTournamentDTOs(Long id) {
        ListDTO<ClientTournamentDTO> resultDTO = new ListDTO<>();
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            resultDTO.setErrorMessage("There is no client with the given id");
        } else {
            resultDTO.setValues(getNotParticipatedTournaments(id).stream()
                    .filter(x -> x.getParticipants().size() < x.getMaxPlayers())
                    .map(this::tournamentToClientTournamentDTO)
                    .collect(Collectors.toList()));
        }
        return resultDTO;
    }

    public ListDTO<ClientReservationDTO> getClientReservationDTOs(Long id) {
        ListDTO<ClientReservationDTO> resultDTO = new ListDTO<>();
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) resultDTO.setErrorMessage("There is no client with the given id");
        else {
            resultDTO.setValues(privateReservationRepository.findAllByClientId(id).stream()
                    .map(this::reservationToClientReservationDTO)
                    .collect(Collectors.toList()));
        }
        return resultDTO;
    }

    public ListDTO<PrivateRentalDTO> getRentalDTOs(Long id) {
        ListDTO<PrivateRentalDTO> resultDTO = new ListDTO<>();
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) resultDTO.setErrorMessage("There is no client with the given id");
        else {
            resultDTO.setValues(privateRentalRepository
                    .findAll()
                    .stream()
                    .filter(rental -> rental.getClientId().equals(client.getId()))
                    .map(PrivateRental::toDTO)
                    .map(dto -> Utils.assignGameNameTo(dto, gameRepository, gameCopyRepository))
                    .collect(Collectors.toList()));
        }
        return resultDTO;
    }

    public ListDTO<ClientDTO> all() {
        ListDTO<ClientDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(clientRepository.findAll().stream()
                .map(Client::toDTO)
                .collect(Collectors.toList()));
        return resultDTO;
    }

    public ClientDTO create(ClientDTO dto) {
        Client client = new Client();
        client.updateParamsFrom(dto);
        try {
            clientRepository.save(client);
            return client.toDTO();
        }
        catch(DataIntegrityViolationException ex) {
            dto.setErrorMessage("Given data violates data constraints");
            return dto;
        }
    }

    public ClientDTO update(ClientDTO dto) {
        return clientRepository.findById(dto.getId())
                .map(existingClient -> {
                    existingClient.updateParamsFrom(dto);

                    try {
                        clientRepository.save(existingClient);
                        return existingClient.toDTO();
                    }
                    catch(DataIntegrityViolationException ex) {
                        dto.setErrorMessage("Given data violates data constraints");
                        return dto;
                    }
                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
