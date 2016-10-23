package com.example.leehyungyu.bnwgameclient.view.gui;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class ChangableGuiContext extends GuiContext {

    private HashMap<Integer, View> frames;

    public ChangableGuiContext(Activity context) {
        super(context);
        frames = new HashMap<>();
    }

    public void registScene(int id) {
        frames.put(id, getView(id));
    }

    public void show(int id) {
        Set<Integer> ids = frames.keySet();

        for(Integer fId : ids)
        {
            if(fId.equals(id))
            {
                frames.get(fId).setVisibility(View.VISIBLE);
            }
            else
            {
                frames.get(fId).setVisibility(View.INVISIBLE);
            }
        }
    }

}
