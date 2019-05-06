package at.jugger.tracker.repository;

import at.jugger.tracker.domain.TournamentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {
    TournamentEntity findByTournamentId(Long id);
}
