package com.example.leehyungyu.bnwgameclient.service.background;

import java.util.HashMap;

/**
 * Created by leehyungyu on 2016-10-25.
 */

public class LocalServiceManager {

    private HashMap<Integer, Thread> services;

    public LocalServiceManager() {
        services = new HashMap<>();
    }

    public void registService(int name, Thread thread) {
        services.put(name, thread);
    }

    public Thread getService(int name) {
        return services.get(name);
    }





}
