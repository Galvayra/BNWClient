package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import android.widget.TextView;

/**
 * Created by leehyungyu on 2016-11-02.
 */

public class ParticipantItem {

    private TextView nickname;
    private TextView win;
    private TextView draw;
    private TextView lose;
    private TextView rate;
    private TextView readyState;

    public TextView getNickname() {
        return nickname;
    }

    public void setNickname(TextView nickname) {
        this.nickname = nickname;
    }

    public TextView getWin() {
        return win;
    }

    public void setWin(TextView win) {
        this.win = win;
    }

    public TextView getDraw() {
        return draw;
    }

    public void setDraw(TextView draw) {
        this.draw = draw;
    }

    public TextView getLose() {
        return lose;
    }

    public void setLose(TextView lose) {
        this.lose = lose;
    }

    public TextView getRate() {
        return rate;
    }

    public void setRate(TextView rate) {
        this.rate = rate;
    }

    public TextView getReadyState() {
        return readyState;
    }

    public void setReadyState(TextView readyState) {
        this.readyState = readyState;
    }
}
