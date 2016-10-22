package com.example.leehyungyu.bnwgameclient.service;

import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public abstract class Service {

    private Callback callback;

    private boolean callbackHooking = false;

    protected abstract Request buildRequest(Object...param);

    public void runOnBackground(final Object...param) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = buildRequest(param);

                Call call = SingletonHttpClient.getInstance().newCall(request);

                if(callbackHooking)
                {
                    call.enqueue(callback);
                }
                else
                {
                    try
                    {
                        call.execute();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public void useCallback(Callback callback)
    {
        this.callback = callback;
        callbackHooking = true;
    }

}
