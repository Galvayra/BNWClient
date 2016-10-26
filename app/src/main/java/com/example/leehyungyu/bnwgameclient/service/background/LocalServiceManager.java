package com.example.leehyungyu.bnwgameclient.service.background;

import java.util.HashMap;

/**
 * Created by leehyungyu on 2016-10-25.
 */

public class LocalServiceManager {

    private HashMap<String, Thread> services;

    public LocalServiceManager() {
        services = new HashMap<>();
    }

    public void registService(String name, Thread thread) {
        services.put(name, thread);
    }

    public Thread getService(String name) {
        return services.get(name);
    }





}
