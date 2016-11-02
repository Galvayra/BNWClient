package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.util.Log;
import android.widget.ListView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListAdapter;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListItemData;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONException;
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

    public void notifyParticipantEnter(JSONObject obj) throws JSONException {
        ParticipantListItemData data = new ParticipantListItemData();
        Log.e("asdasd", obj.toString());
        data.setNickname(JsonUtils.get(obj,"nickname").toString());
        data.setWin((int)JsonUtils.get(obj,"win"));
        data.setDraw((int)JsonUtils.get(obj,"draw"));
        data.setLose((int)JsonUtils.get(obj,"lose"));
        data.setWinRate(obj.getDouble("rate"));
        data.setReady((boolean)JsonUtils.get(obj,"ready"));
        ((ParticipantListAdapter)gtx.getView(R.id.participant_list, ListView.class).getAdapter()).addItem(data);
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ParticipantListAdapter)gtx.getView(R.id.participant_list, ListView.class).getAdapter()).notifyDataSetChanged();
            }
        });
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
        ((ParticipantListAdapter)gtx.getView(R.id.participant_list, ListView.class).getAdapter()).removeItem(1);
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ParticipantListAdapter)gtx.getView(R.id.participant_list, ListView.class).getAdapter()).notifyDataSetChanged();
                gtx.appendText(R.id.chat_area, "플레이어가 퇴장했습니다.");
            }
        });
    }
}
