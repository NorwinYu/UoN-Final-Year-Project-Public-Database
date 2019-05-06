package com.tribesbackend.tribes.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "troops")
public class Troop {
    @Id
    @GeneratedValue
    private Long id;
    @Min(value = 0L, message = "The value must be positive")
    @NotNull
    private int level;
    @Min(value = 0L, message = "The value must be positive")
    @NotNull
    private int hp;
    @Min(value = 0L, message = "The value must be positive")
    @NotNull
    private int attack;
    @Min(value = 0L, message = "The value must be positive")
    @NotNull
    private int defence;
    private long startedAt;
    private long finishedAt;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public Troop() {
    }

    public Troop(int level, int hp, int attack, int defence) {
        this.level = level;
        this.hp = hp;
        this.attack = attack;
        this.defence = defence;
        startedAt = (new Timestamp(System.currentTimeMillis())).getTime();
    }

    public static class TroopBuilder {
        @Id
        @GeneratedValue
        private Long id;
        @Min(value = 0L, message = "The value must be positive")
        @NotNull
        private int level;
        @Min(value = 0L, message = "The value must be positive")
        @NotNull
        private int hp;
        @Min(value = 0L, message = "The value must be positive")
        @NotNull
        private int attack;
        @Min(value = 0L, message = "The value must be positive")
        @NotNull
        private int defence;
        private int startedAt;
        private long finishedAt;
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "kingdom_id")
        private Kingdom kingdom;

        public TroopBuilder() {
        }

        public TroopBuilder setKingdom(Kingdom kingdom) {
            this.kingdom = kingdom;
            return this;
        }

        public TroopBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public TroopBuilder setHp(int hp) {
            this.hp = hp;
            return this;
        }

        public TroopBuilder setAttack(int attack) {
            this.attack = attack;
            return this;
        }

        public TroopBuilder setDefence(int defence) {
            this.defence = defence;
            return this;
        }

        public TroopBuilder setStartedAt(int startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public TroopBuilder setFinishedAt(long finishedAt) {
            this.finishedAt = finishedAt;
            return this;
        }

        public Troop build() {
            return new Troop(level, hp, attack, defence);
        }
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public Long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public Troop setId(Long id) {
        this.id = id;
        return this;
    }

    public Troop setLevel(int level) {
        this.level = level;
        return this;
    }

    public Troop setHp(int hp) {
        this.hp = hp;
        return this;
    }

    public Troop setAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public Troop setDefence(int defence) {
        this.defence = defence;
        return this;
    }

    public Troop setStartedAt(long startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public Troop setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public Troop setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
        return this;
    }
}
