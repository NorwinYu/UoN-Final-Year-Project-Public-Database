package pl.put.boardgamemanager.game_copy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameCopyRepository extends JpaRepository<GameCopy, Long> {

    List<GameCopy> findAllByGameId(Long gameId);

}
