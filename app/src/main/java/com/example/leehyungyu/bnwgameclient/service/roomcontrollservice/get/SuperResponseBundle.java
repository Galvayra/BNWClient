package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.widget.ListView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONObject;

/**
 * Created by leehyungyu on 2016-10-31.
 */

public class SuperResponseBundle {

    private GuiContext gtx;

    public SuperResponseBundle(GuiContext gtx) {
        this.gtx = gtx;
    }

    public void startResponse(JSONObject obj) {
        boolean response = (boolean) JsonUtils.get(obj, "result");
        if(response)
        {
            gtx.showToast("게임 시작 가능!");
        }
        else
        {
            gtx.showToast("참가자가 ready를 하지 않았습니다.");
        }
    }

    public void notifyParticipantEnter(JSONObject obj) {
        gtx.showToast("새로운 유저 들어옴, 리스트에 추가하는 작업 해야함");
        //gtx.getView(R.id.participant_list, ListView.class).getAdapter().notify();
    }

}
