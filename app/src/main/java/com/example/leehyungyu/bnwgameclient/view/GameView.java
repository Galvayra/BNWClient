package com.example.leehyungyu.bnwgameclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

/**
 * Created by leehyungyu on 2016-11-02.
 */

public class GameView extends AppCompatActivity {

    private GuiContext gtx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        gtx = new GuiContext(this);
    }

}
