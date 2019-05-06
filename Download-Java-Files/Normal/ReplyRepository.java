package api.iti0208.repository;

import api.iti0208.data.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.reply =:reply WHERE r.id =:id")
    void updateReply(@Param("id") Long id, @Param("reply") String reply);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.id =:id")
    void deleteReply(@Param("id") Long id);

}
