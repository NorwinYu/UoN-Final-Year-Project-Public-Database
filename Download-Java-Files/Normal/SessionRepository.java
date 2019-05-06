package propra2.leihOrDie.dataaccess;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import propra2.leihOrDie.model.Session;
import propra2.leihOrDie.model.User;

import javax.validation.constraints.Past;
import java.util.List;

public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findAll();

    @Query("SELECT s.user FROM Session s WHERE s.sessionId = :sessionId")
    User findUserBySessionCookie(@Param("sessionId") String sessionId);
}
