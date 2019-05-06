package at.jugger.tracker.service.impl;

import at.jugger.tracker.domain.TournamentEntity;
import at.jugger.tracker.dto.Tournament;
import at.jugger.tracker.dto.TournamentData;
import at.jugger.tracker.exceptions.TournamentNotFoundException;
import at.jugger.tracker.mapper.TournamentCreateMapper;
import at.jugger.tracker.mapper.TournamentUpdateMapper;
import at.jugger.tracker.repository.TournamentRepository;
import at.jugger.tracker.service.TournamentService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentCreateMapper tournamentCreateMapper;
    private final TournamentUpdateMapper tournamentUpdateMapper;

    TournamentServiceImpl(TournamentRepository tournamentRepository, TournamentCreateMapper tournamentCreateMapper, TournamentUpdateMapper tournamentUpdateMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentCreateMapper = tournamentCreateMapper;
        this.tournamentUpdateMapper = tournamentUpdateMapper;
    }

    @Override
    public Tournament getTournament(@NotNull Long id) {
        return tournamentCreateMapper.toDto(tournamentRepository.findByTournamentId(id));
    }

    @Override
    public @NotNull List<Tournament> getTournaments() {
        return tournamentCreateMapper.toDtos(tournamentRepository.findAll());
    }

    @Override
    public @NotNull Tournament createTournament(@NotNull TournamentData newTournament) {
        TournamentEntity tournamentEntity = tournamentCreateMapper.toEntity(newTournament);

        tournamentEntity = tournamentRepository.save(tournamentEntity);

        return tournamentCreateMapper.toDto(tournamentEntity);
    }

    @Override
    public @NotNull Tournament updateTournament(Long id, TournamentData tournament) {
        TournamentEntity tournamentEntity = tournamentRepository.findByTournamentId(id);

        if (tournamentEntity == null) {
            throw new TournamentNotFoundException("id", Long.toString(id));
        }

        tournamentEntity = tournamentUpdateMapper.toEntity(tournament, tournamentEntity);
        tournamentEntity = tournamentRepository.save(tournamentEntity);

        return tournamentUpdateMapper.toDto(tournamentEntity);
    }

    @Override
    public void deleteTournament(@NotNull Long id) {
        tournamentRepository.deleteById(id);
    }


}
