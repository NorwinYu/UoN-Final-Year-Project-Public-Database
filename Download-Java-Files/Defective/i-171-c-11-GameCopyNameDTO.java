package pl.put.boardgamemanager.game_copy;

import pl.put.boardgamemanager.DTO;

public class GameCopyNameDTO  extends DTO {

    private String name;

    private Long copyId;

    public boolean validate() {
        if(name == null) {
            this.setErrorMessage("Name cannot be null");
            return false;
        }
        if(name.trim().isEmpty()) {
            this.setErrorMessage("Name cannot be blank");
            return false;
        }
        if(copyId == null) {
            this.setErrorMessage("copyId cannot be null");
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

}
