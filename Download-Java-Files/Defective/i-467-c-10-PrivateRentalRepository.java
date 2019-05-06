package pl.put.boardgamemanager.private_rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateRentalRepository extends JpaRepository<PrivateRental, Long> {

}