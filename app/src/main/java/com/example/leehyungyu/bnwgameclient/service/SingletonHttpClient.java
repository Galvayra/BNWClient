package com.example.leehyungyu.bnwgameclient.service;

import okhttp3.OkHttpClient;

/**
 * Created by leehyungyu on 2016-10-22.
 */

public class SingletonHttpClient {

    private static OkHttpClient client = null;

    public static OkHttpClient getInstance() {
        if(client==null)
        {
            client = new OkHttpClient();
        }
        return client;
    }
}
