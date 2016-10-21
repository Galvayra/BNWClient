package com.example.leehyungyu.bnwgameclient.service.callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public abstract class JsonResponseCallback implements ResponseCallback {
    @Override
    public void onResponse(Response response) {}

    public abstract void onJsonResponse(JSONObject responseObject) throws JSONException;
}
