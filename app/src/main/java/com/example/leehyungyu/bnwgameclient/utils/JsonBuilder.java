package com.example.leehyungyu.bnwgameclient.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class JsonBuilder {

    private String[] keys;
    private Object[] values;
    private JSONObject obj;

    public JsonBuilder addKeys(String..._keys) {
        keys = _keys;
        return this;
    }

    public JsonBuilder addValues(Object..._values) {
        values = _values;
        return this;
    }

    public JSONObject build() {
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

    public String toJsonString() {
        return build().toString();
    }
}
