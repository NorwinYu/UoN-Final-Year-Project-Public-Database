package pl.put.boardgamemanager.game;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import pl.put.boardgamemanager.ListDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

    public GameDTO get(Long id) {
        Game game = repository.findById(id).orElse(null);
        if (game == null) {
            GameDTO dto = new GameDTO();
            dto.setErrorMessage("There is no game with the given id");
            return dto;
        } else return game.toDTO();
    }

    public ListDTO<GameDTO> all() {
        ListDTO<GameDTO> resultDTO = new ListDTO<>();
        resultDTO.setValues(repository.findAll().stream()
                .map(Game::toDTO)
                .collect(Collectors.toList()));
        return resultDTO;
    }

    public GameDTO create(GameDTO dto) {
        Game game = new Game();
        game.updateParamsFrom(dto);
        try {
            repository.save(game);
            return game.toDTO();
        }
        catch(DataIntegrityViolationException ex) {
            dto.setErrorMessage("Given data violates data constraints");
            return dto;
        }
    }

    public GameDTO update(GameDTO dto) {
        return repository.findById(dto.getId())
                .map(existingGame -> {
                    existingGame.updateParamsFrom(dto);

                    try {
                        repository.save(existingGame);
                        return existingGame.toDTO();
                    }
                    catch(DataIntegrityViolationException ex) {
                        dto.setErrorMessage("Given data violates data constraints");
                        return dto;
                    }
                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
