package com.clocker.clocker.payload;

import javax.validation.constraints.*;

public class TimerUpdateRequest {
    @NotBlank
    @Size(min = 1, max = 40)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}