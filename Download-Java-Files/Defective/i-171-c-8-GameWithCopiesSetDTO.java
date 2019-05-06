package pl.put.boardgamemanager.game;

import pl.put.boardgamemanager.DTO;
import pl.put.boardgamemanager.game_copy.GameCopy;

import java.util.List;

public class GameWithCopiesSetDTO  extends DTO {

    private Game game;

    private List<GameCopy> gameCopies;

    public boolean validate() {
        if(game == null) {
            this.setErrorMessage("Game cannot be null");
            return false;
        }
        if(gameCopies == null) {
            this.setErrorMessage("GameCopies cannot be null");
            return false;
        }
        return true;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<GameCopy> getGameCopies() {
        return gameCopies;
    }

    public void setGameCopies(List<GameCopy> gameCopies) {
        this.gameCopies = gameCopies;
    }

}
