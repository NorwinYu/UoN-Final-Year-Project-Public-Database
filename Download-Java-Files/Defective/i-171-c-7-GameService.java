package pl.put.boardgamemanager.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.boardgamemanager.ListDTO;

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
        repository.save(game);
        return game.toDTO();
    }

    public GameDTO update(GameDTO dto) {
        return repository.findById(dto.getId())
                .map(existingGame -> {
                    existingGame.updateParamsFrom(dto);
                    repository.save(existingGame);
                    return existingGame.toDTO();
                })
                .orElseGet(() -> create(dto));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
