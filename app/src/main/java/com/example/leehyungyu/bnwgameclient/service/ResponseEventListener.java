package com.example.leehyungyu.bnwgameclient.service;

import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public interface ResponseEventListener {
    public void onResponse(Response response);
}
