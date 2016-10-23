package com.example.leehyungyu.bnwgameclient.service;

import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class GetRoomListService extends Service {

    public GetRoomListService(GuiContext gtx) {
        super(gtx);
    }

    @Override
    protected Callback buildCallback(Object... param) {
        return null;
    }

    @Override
    protected Request buildRequest(Object... param) {
        return null;
    }
}
