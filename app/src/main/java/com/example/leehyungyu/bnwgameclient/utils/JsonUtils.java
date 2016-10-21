package com.example.leehyungyu.bnwgameclient.utils;

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
}
