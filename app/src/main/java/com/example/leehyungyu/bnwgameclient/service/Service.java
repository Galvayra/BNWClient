package com.example.leehyungyu.bnwgameclient.service;

import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public abstract class Service {

    protected abstract Request buildRequest(Object...param);
    protected abstract Callback buildCallback();

    private GuiContext guiContext;
    private String serviceOwner;

    public Service(GuiContext guiContext, String serviceOwner) {
        this.guiContext = guiContext;
        this.serviceOwner = serviceOwner;
    }

    public void setGuiContext(GuiContext guiContext) {
        this.guiContext = guiContext;
    }

    public GuiContext getGuiContext() {
        return guiContext;
    }

    public void runOnBackground(final Object...param) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = buildRequest(param);

                Callback callback = buildCallback();

                if(callback!=null)
                {
                    SingletonHttpClient.getInstance().newCall(request).enqueue(callback);
                }
            }
        }).start();
    }

    public RequestBody jsonRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public String getServiceOwner() {
        return serviceOwner;
    }
}
