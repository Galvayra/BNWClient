package com.example.leehyungyu.bnwgameclient.view.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leehyungyu.bnwgameclient.utils.Extras;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class GuiContext {

    private Activity context;

    private HashMap<String, View> views;

    public GuiContext(Activity context) {
        this.context = context;
        this.views = new HashMap<>();
    }

    public void registView(String key, View view) {
        views.put(key, view);
    }

    public <T> T getView(String key, Class<T> clazz) {
        return (T)views.get(key);
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public void showToast(final String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeActivity(final Class<?> clazz, final Extras extras) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(context, clazz);

                if(extras!=null)
                {
                    Set<String> keys = extras.getExtras().keySet();
                    for(String key : keys)
                    {
                        intent.putExtra(key, extras.getExtras().get(key).toString());
                    }
                }

                context.startActivity(intent);
            }
        });
    }

    public View getView(int id) {
        return context.findViewById(id);
    }

    public <T> T getView(int id, Class<T> clazz) {
        return (T)context.findViewById(id);
    }

    public String getTextFromView(int id) {
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

    public Activity getContext() {
        return context;
    }

    public void click(int id, View.OnClickListener r) {
        context.findViewById(id).setOnClickListener(r);
    }

}
