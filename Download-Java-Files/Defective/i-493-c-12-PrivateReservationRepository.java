package pl.put.boardgamemanager.private_reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateReservationRepository extends JpaRepository<PrivateReservation, Long> {

    List<PrivateReservation> findAllByClientId(Long clientId);

}
