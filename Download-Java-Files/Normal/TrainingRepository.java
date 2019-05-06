package at.jugger.tracker.repository;

import at.jugger.tracker.domain.TrainingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface TrainingRepository extends JpaRepository<TrainingEntity, Long> {
    TrainingEntity findByDate(LocalDate date);

    @Query(value = "delete from user_participates_training where user_id = ?1", nativeQuery = true)
    void deleteAllParticipationsForUser(Long id);
}
