package com.example.leehyungyu.bnwgameclient.service.login;

import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class LoginService extends Service {

    @Override
    protected Request buildRequest(Object... param) {

        JsonBuilder jb = new JsonBuilder();
        jb.addKeys("id","password").addValues(param[0], param[1]);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jb.toJsonString());
        Request request = new Request.Builder().url(ServerConfiguration.LOGIN_REQUEST_URI).post(body).build();

        useCallback(new LoginCallback());

        return request;
    }

}
