package com.example.leehyungyu.bnwgameclient.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class JsonBuilder {

    private static String[] keys;
    private static Object[] values;
    public static void addKeys(String..._keys) {
        keys = _keys;
    }

    public static void addValues(Object..._values) {
        values = _values;
    }

    public static JSONObject build() {
        JSONObject obj = new JSONObject();
        for(int i = 0; i<keys.length; i++)
        {
            try
            {
                obj.put(keys[i],values[i]);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
        return obj;
    }

}
