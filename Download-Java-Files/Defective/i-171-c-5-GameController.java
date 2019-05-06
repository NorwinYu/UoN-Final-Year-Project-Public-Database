package pl.put.boardgamemanager.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.put.boardgamemanager.ListDTO;

import java.util.List;

@RestController
@CrossOrigin
public class GameController {

    @Autowired
    private GameService service;

    @GetMapping("/games/{id}")
    public GameDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/games")
    public ListDTO<GameDTO> all() {
        return service.all();
    }

    @PostMapping("/games")
    public GameDTO create(@RequestBody GameDTO gameDTO) {
        if(!gameDTO.validate()) return gameDTO;
        else return service.create(gameDTO);
    }

    @PutMapping("/games")
    public GameDTO update(@RequestBody GameDTO gameDTO) {
        if(gameDTO.getId() == null) {
            gameDTO.setErrorMessage("Id in updating cannot be null");
            return gameDTO;
        }
        if(!gameDTO.validate()) return gameDTO;
        else return service.update(gameDTO);
    }

    @DeleteMapping("/games/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
