package pl.put.boardgamemanager.game_copy;

import pl.put.boardgamemanager.DTO;

public class GameCopyDTO extends DTO {

    private Long id;

    private Long gameId;

    public boolean validate() {

        if(gameId == null) {
            this.setErrorMessage("gameId cannot be null");
            return false;
        }
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

}
