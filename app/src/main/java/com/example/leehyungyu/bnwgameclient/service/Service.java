package com.example.leehyungyu.bnwgameclient.service;

import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public abstract class Service {

    protected abstract Request buildRequest(Object...param);
    protected abstract Callback buildCallback(Object...param);
    private GuiContext guiContext;

    public Service(GuiContext guiContext) {
        this.guiContext = guiContext;
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

                Callback callback = buildCallback(param);

                if(callback!=null)
                {
                    SingletonHttpClient.getInstance().newCall(request).enqueue(callback);
                }

            }
        }).start();
    }
}
