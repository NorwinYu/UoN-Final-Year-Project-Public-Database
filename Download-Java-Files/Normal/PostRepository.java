package api.iti0208.repository;

import api.iti0208.data.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where LOWER(p.topic) = LOWER(:topic)")
    Page<Post> findAllByTopic(@Param("topic") String topic, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE " +
           "LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Post p set p.title =:title where p.id =:id")
    void updateTitle(@Param("id") Long id, @Param("title") String title);

    @Modifying
    @Transactional
    @Query("update Post p set p.description =:description where p.id =:id")
    void updateDescription(@Param("id") Long id, @Param("description") String description);

}
