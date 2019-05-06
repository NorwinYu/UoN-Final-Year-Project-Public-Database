package propra2.leihOrDie.dataaccess;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import propra2.leihOrDie.model.Picture;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Long> {
    List<Picture> findAll();

    @Query("SELECT p FROM Picture p WHERE p.item.id = :itemId")
    List<Picture> findPicturesOfItem(@Param("itemId") Long itemId);
}
