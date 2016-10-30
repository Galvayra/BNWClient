package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.graphics.Color;
import android.widget.Button;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONObject;

/**
 * Created by leehyungyu on 2016-10-31.
 */

public class ParticipantResponseBundle {

    private GuiContext gtx;

    public ParticipantResponseBundle(GuiContext gtx) {
        this.gtx = gtx;
    }

    public void readyResponse(JSONObject obj) {
        String result = JsonUtils.get(obj, "result").toString();

        final String buttonFaceText;
        final int color;

        if(result.equals("ready"))
        {
            buttonFaceText = "준비취소";
            color = Color.RED;
        }
        else
        {
            buttonFaceText = "준비하기";
            color = Color.GREEN;
        }
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button readyBtn = gtx.getView(R.id.game_ready_btn, Button.class);
                readyBtn.setText(buttonFaceText);
                readyBtn.setBackgroundColor(color);
            }
        });
    }

}
