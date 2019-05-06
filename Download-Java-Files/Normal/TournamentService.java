package at.jugger.tracker.service;

import at.jugger.tracker.dto.Tournament;
import at.jugger.tracker.dto.TournamentData;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TournamentService {
    Tournament getTournament(@NotNull Long id);

    @NotNull List<Tournament> getTournaments();

    @NotNull Tournament createTournament(@NotNull TournamentData newTournament);

    @NotNull Tournament updateTournament(Long id, @NotNull TournamentData tournament);

    void deleteTournament(@NotNull Long id);
}
