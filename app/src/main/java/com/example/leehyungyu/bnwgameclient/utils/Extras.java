package com.example.leehyungyu.bnwgameclient.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by leehyungyu on 2016-10-22.
 */

public class Extras {

    HashMap<String, Object> extras;

    public Extras() {
        extras = new HashMap<>();
    }

    public Extras addExtra(String key, Object value) {
        extras.put(key, value);
        return this;
    }

    public HashMap<String, Object> getExtras() {
        return extras;
    }

}
