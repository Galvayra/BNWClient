package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

/**
 * Created by leehyungyu on 2016-10-24.
 */

public enum RoomState {
    WAIT(1),
    FULL(2),
    ON_GAME(3);

    private final int value;

    RoomState(int value)
    {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RoomState valueOf(int value) {
        switch(value)
        {
            case 1 : return WAIT;
            case 2 : return FULL;
            case 3 : return ON_GAME;
            default : return null;
        }
    }
}
