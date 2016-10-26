package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.widget.TextView;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class RoomItem {
    private TextView roomNumber;
    private TextView roomTitle;
    private TextView roomCreator;
    private TextView numberOfParticipant;
    private TextView roomState;

    public TextView getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(TextView roomNumber) {
        this.roomNumber = roomNumber;
    }

    public TextView getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(TextView roomTitle) {
        this.roomTitle = roomTitle;
    }

    public TextView getRoomCreator() {
        return roomCreator;
    }

    public void setRoomCreator(TextView roomCreator) {
        this.roomCreator = roomCreator;
    }

    public TextView getNumberOfParticipant() {
        return numberOfParticipant;
    }

    public void setNumberOfParticipant(TextView numberOfParticipant) {
        this.numberOfParticipant = numberOfParticipant;
    }

    public TextView getRoomState() {
        return roomState;
    }

    public void setRoomState(TextView roomState) {
        this.roomState = roomState;
    }
}
