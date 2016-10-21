package com.example.leehyungyu.bnwgameclient.service;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public abstract class Service {
    OkHttpClient client = new OkHttpClient();

    Response response;
    ResponseEventListener responseEventListener;
    protected abstract Response execute(Object...param);

    public OkHttpClient getClient() {
        return client;
    }

    public void runOnBackground(final Object...param) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                response = execute(param);
                responseEventListener.onResponse(response);
            }
        }).start();
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setOnResponse(ResponseEventListener responseEventListener) {
        this.responseEventListener = responseEventListener;
    }
}
