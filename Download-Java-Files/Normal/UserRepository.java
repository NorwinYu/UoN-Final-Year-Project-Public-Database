package api.iti0208.repository;

import api.iti0208.data.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    @Query("select a.id from AppUser a where a.username =:username")
    Long findIdByUsername(@Param("username") String username);

}
