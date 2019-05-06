package at.jugger.tracker.controller;

import at.jugger.tracker.api.TournamentApiDelegate;
import at.jugger.tracker.dto.Tournament;
import at.jugger.tracker.dto.TournamentData;
import at.jugger.tracker.exceptions.TournamentNotFoundException;
import at.jugger.tracker.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class TournamentController implements TournamentApiDelegate {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @Override
    public ResponseEntity<Tournament> getTournament(@NotNull Long id) {
        Tournament tournament = tournamentService.getTournament(id);

        if (tournament == null) {
            throw new TournamentNotFoundException("id", Long.toString(id));
        }

        return ResponseEntity.ok(tournamentService.getTournament(id));
    }

    @Override
    public ResponseEntity<Tournament> createTournament(@NotNull TournamentData newTournament) {
        return ResponseEntity.ok(tournamentService.createTournament(newTournament));
    }

    @Override
    public ResponseEntity<Void> deleteTournament(@NotNull Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Tournament>> getTournaments() {
        return ResponseEntity.ok(tournamentService.getTournaments());
    }

    @Override
    public ResponseEntity<Tournament> updateTournament(@NotNull Long id, @NotNull TournamentData tournament) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournament));
    }
}
