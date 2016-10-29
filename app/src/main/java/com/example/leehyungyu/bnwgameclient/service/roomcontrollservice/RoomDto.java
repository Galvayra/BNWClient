package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import java.io.Serializable;

public class RoomDto implements Serializable {
	private int room_no;
	private String creator;
	private String participant;
	private int room_state;
	private String room_title;
	
	public RoomDto(){}

	public RoomDto(int room_no, String creator, String participant, int room_state, String room_title) {
		this.room_no = room_no;
		this.creator = creator;
		this.participant = participant;
		this.room_state = room_state;
		this.room_title = room_title;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public int getRoom_state() {
		return room_state;
	}

	public void setRoom_state(int room_state) {
		this.room_state = room_state;
	}

	public String getRoom_title() {
		return room_title;
	}

	public void setRoom_title(String room_title) {
		this.room_title = room_title;
	}

	@Override
	public String toString() {
		return "RoomDto [room_no=" + room_no + ", creator=" + creator + ", participant=" + participant + ", room_state="
				+ room_state + ", room_title=" + room_title + "]";
	}
	
}
