package com.example.leehyungyu.bnwgameclient.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import static com.example.leehyungyu.bnwgameclient.utils.ReflectionUtils.*;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class JsonUtils {

    public static <T> T mappingObject(JSONObject obj, Class<T> clazz) {

        T t = createInstance(clazz);

        for(Iterator<String> i = obj.keys(); i.hasNext();)
        {
            String key = i.next();
            Log.e("jsonutil-obj-mapping", key);
            try
            {
                setField(t, key, obj.get(key));
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

        }

        return t;
    }

    public static JSONArray parseJsonArray(String str) {
        JSONArray arr = null;
        try
        {
            arr = new JSONArray(str);
        }
        catch(JSONException e)
        {
            return null;
        }
        return arr;
    }

    public static JSONObject parseJsonObject(String str) {
        JSONObject obj = null;
        try
        {
            obj = new JSONObject(str);
        }
        catch (JSONException e)
        {
            return null;
        }
        return obj;
    }

    public static Object get(JSONObject obj, String key) {

        Object value = null;

        try
        {
            value = obj.get(key);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return value;
    }

    public static JSONObject get(JSONArray array, int idx) {
        JSONObject obj = null;

        try
        {
            obj = array.getJSONObject(idx);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return obj;
    }

}
