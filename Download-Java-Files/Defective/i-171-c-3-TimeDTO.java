package pl.put.boardgamemanager;

import java.time.LocalDateTime;

public class TimeDTO extends DTO {

    private LocalDateTime startTime;

    private Integer duration;

    public boolean validate() {
        if(startTime == null) {
            this.setErrorMessage("No startTime given");
            return false;
        }

        if(duration == null) {
            this.setErrorMessage("No duration given");
            return false;
        }

        return true;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
