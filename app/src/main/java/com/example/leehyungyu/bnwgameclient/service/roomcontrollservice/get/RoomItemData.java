package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class RoomItemData {
    private int roomNumber;
    private String roomTitle;
    private String roomState;
    private String numOfPart;
    private String roomCreator;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public String getNumOfPart() {
        return numOfPart;
    }

    public void setNumOfPart(String numOfPart) {
        this.numOfPart = numOfPart;
    }

    public String getRoomCreator() {
        return roomCreator;
    }

    public void setRoomCreator(String roomCreator) {
        this.roomCreator = roomCreator;
    }
}
