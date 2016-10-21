package com.example.leehyungyu.bnwgameclient.service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class LoginService extends Service {

    @Override
    protected Response execute(Object... param) {
        OkHttpClient client = getClient();

        try
        {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),param[0].toString());
            Request request = new Request.Builder().url("http://172.30.1.59:8080/bnwserver/mobile/login ").post(body).build();
            Response res = client.newCall(request).execute();
            return res;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
