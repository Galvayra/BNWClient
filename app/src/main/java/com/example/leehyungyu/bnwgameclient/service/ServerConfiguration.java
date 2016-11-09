package com.example.leehyungyu.bnwgameclient.service;

/**
 * Created by leehyungyu on 2016-10-22.
 */

public class ServerConfiguration {
    public static final String HTTP = "http://";
    public static final String WS = "ws:";

    public static final String HOST_WITHOUT_CONTEXT = "172.16.42.76:8080/";
    public static final String HOST = HOST_WITHOUT_CONTEXT+"bnwserver/";

    public static final String LOGIN_REQUEST_URI = HTTP+HOST+"mobile/login";
    public static final String MYPAGE_VIEW_REQUEST = HTTP+HOST+"mobile/mypage";
    public static final String GET_GAME_ROOM_REQUEST = HTTP+HOST+"mobile/room";
    public static final String CREATE_GAME_ROOM_REQUEST = HTTP+HOST+"mobile/createroom";
    public static final String ENTER_GAME_ROOM_REQUEST = HTTP+HOST+"mobile/enterroom";
    public static final String ROOM_STATE_GET_REQUEST = HTTP+HOST+"mobile/getroomstate";
    public static final String ROOM_CONTROLL_SERVER = WS + HOST + "echo";

}
