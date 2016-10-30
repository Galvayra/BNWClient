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

    public void notifyParticipantReady(JSONObject obj) {
        String result = JsonUtils.get(obj, "result").toString();
        final String notifyString;

        if(result.equals("ready"))
        {
            notifyString = "[ SYSTEM ] 플레이어가 준비 완료했습니다. 게임을 시작할 수 있습니다.";
        }
        else
        {
            notifyString = "[ SYSTEM ] 플레이어 준비 취소";
        }
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gtx.appendText(R.id.chat_area, notifyString);
            }
        });
    }

    public void notifyParticipantOut() {
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gtx.appendText(R.id.chat_area, "플레이어가 퇴장했습니다.");
            }
        });
    }
}
