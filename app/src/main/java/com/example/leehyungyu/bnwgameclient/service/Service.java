package com.example.leehyungyu.bnwgameclient.service;

import com.example.leehyungyu.bnwgameclient.service.callback.JsonResponseCallback;
import com.example.leehyungyu.bnwgameclient.service.callback.ResponseCallback;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public abstract class Service {
    OkHttpClient client = new OkHttpClient();

    Response response;
    ResponseCallback responseCallback;
    protected abstract Response execute(Object...param);

    public OkHttpClient getClient() {
        return client;
    }

    public void runOnBackground(final Object...param) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                response = execute(param);
                if(response!=null)
                {
                    runSuccessCallback(response);
                }

             }
        }).start();
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setOnResponse(ResponseCallback responseCallback) {
        this.responseCallback = responseCallback;
    }

    public String extractBodyFromResponse(Response response) {
        String body = null;
        try
        {
            body = response.body().string();
        }
        catch (IOException e)
        {
            return null;
        }
        return body;
    }

    public void runSuccessCallback(Response response) {
        if(responseCallback instanceof JsonResponseCallback)
        {
            String responseJson = extractBodyFromResponse(response);
            try
            {
                ((JsonResponseCallback) responseCallback).onJsonResponse(JsonUtils.parseJsonObject(responseJson));
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            responseCallback.onResponse(response);
        }
    }


}
