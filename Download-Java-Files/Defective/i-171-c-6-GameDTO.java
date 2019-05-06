package pl.put.boardgamemanager.game;

import pl.put.boardgamemanager.DTO;

public class GameDTO extends DTO {

    private Long id;

    private String name;

    private String publisher;

    private Short minPlayers;

    private Short maxPlayers;

    private Integer avgTime;

    private String description;

    public boolean validate() {
        if(name == null) {
            this.setErrorMessage("Name cannot be null");
            return false;
        }
        if(name.trim().isEmpty()) {
            this.setErrorMessage("Name cannot be blank");
            return false;
        }
        if(publisher == null) {
            this.setErrorMessage("Publisher cannot be null");
            return false;
        }
        if(publisher.trim().isEmpty()) {
            this.setErrorMessage("Publisher cannot be blank");
            return false;
        }
        if(minPlayers == null || maxPlayers == null) {
            this.setErrorMessage("Number of players cannot be null");
            return false;
        }
        if(minPlayers <= 0 || maxPlayers <= 0) {
            this.setErrorMessage("Number of players must be positive");
            return false;
        }
        if(minPlayers > maxPlayers) {
            this.setErrorMessage("Minimal number of players must be less or equal to the maximal");
            return false;
        }
        if(avgTime == null) {
            this.setErrorMessage("avgTime cannot be null");
            return false;
        }
        if(avgTime <= 0) {
            this.setErrorMessage("Average game time must be greater than 0");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Short getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Short minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Short getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Short maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Integer getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Integer avgTime) {
        this.avgTime = avgTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
