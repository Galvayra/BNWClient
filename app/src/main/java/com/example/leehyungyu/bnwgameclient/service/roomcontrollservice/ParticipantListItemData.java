package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import java.io.Serializable;

/**
 * Created by leehyungyu on 2016-11-02.
 */

public class ParticipantListItemData implements Serializable {
    private String id;
    private String nickname;
    private int win;
    private int draw;
    private int lose;
    private double winRate;
    private boolean isReady;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    @Override
    public String toString() {
        return "ParticipantListItemData{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", win=" + win +
                ", draw=" + draw +
                ", lose=" + lose +
                ", winRate=" + winRate +
                ", isReady=" + isReady +
                '}';
    }
}
