package com.clocker.clocker.payload;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String avatar;
    private String email;

    public UserSummary(Long id, String username, String name, String avatar, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}