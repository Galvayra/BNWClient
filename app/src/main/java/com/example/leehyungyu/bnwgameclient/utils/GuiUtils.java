package com.example.leehyungyu.bnwgameclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class GuiUtils {

    private static Activity context;

    public static void setContext(Activity _context) {
        context = _context;
    }

    public static void showToast(final String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void changeActivity(final Class<?> clazz, Object...param) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(context, clazz);
                context.startActivity(intent);
            }
        });
    }

    public static <T> T getView(int id, Class<T> clazz) {
        return (T)context.findViewById(id);
    }

    public static String getTextFromView(int id) {
        View view = context.findViewById(id);

        if(view instanceof EditText)
        {
            return ((EditText)view).getText().toString();
        }
        else if(view instanceof TextView)
        {
            return ((TextView)view).getText().toString();
        }
        return null;
    }

}
