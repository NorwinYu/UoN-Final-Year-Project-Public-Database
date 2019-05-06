package com.tribesbackend.tribes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buildingtime")
public class BuildingTime {
    @Id
    @GeneratedValue
    Long id;
    private String type;
    private int buildTimeInMin;

    public BuildingTime() {
    }

    public BuildingTime(String type, int buildTimeInMin) {
        this.type = type;
        this.buildTimeInMin = buildTimeInMin;
    }

    public Long getId() {
        return id;
    }

    public BuildingTime setId(Long id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public BuildingTime setType(String type) {
        this.type = type;
        return this;
    }

    public int getBuildTimeInMin() {
        return buildTimeInMin;
    }

    public BuildingTime setBuildTimeInMin(int buildTimeInMin) {
        this.buildTimeInMin = buildTimeInMin;
        return this;
    }
}
