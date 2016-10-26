package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.create;

import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by leehyungyu on 2016-10-26.
 */

public class CreateRoomService extends Service {

    public CreateRoomService(GuiContext context) {
        super(context);
    }

    @Override
    protected Request buildRequest(Object... param) {
        return null;
    }

    @Override
    protected Callback buildCallback() {
        return null;
    }
}
