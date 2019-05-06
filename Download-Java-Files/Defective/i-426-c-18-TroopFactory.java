package com.tribesbackend.tribes.factories;

import com.tribesbackend.tribes.models.Troop;

public class TroopFactory {
    public static Troop createSampleTroop() {
        return new Troop.TroopBuilder()
                .setLevel(1)
                .setHp(100)
                .setAttack(50)
                .setDefence(20)
                .build();
    }

    public static Troop createInvalidSampleTroop() {
        return new Troop.TroopBuilder()
                .setLevel(1)
                .setHp(100)
                .setAttack(-50)
                .setDefence(20)

                .build();
    }
}
