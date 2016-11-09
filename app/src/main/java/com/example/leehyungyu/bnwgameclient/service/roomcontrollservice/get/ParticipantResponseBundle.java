package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListItemData;
import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.GameView;
import com.example.leehyungyu.bnwgameclient.view.UserMainView;
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

    public void setGuiContext(GuiContext gtx) {
        this.gtx = gtx;
    }

    public void readyResponse(JSONObject obj) {
        String result = JsonUtils.get(obj, "result").toString();

        final String buttonFaceText;
        final int color;
        final boolean ready;


        if(result.equals("ready"))
        {
            ready = false;
            buttonFaceText = "준비취소";
            color = Color.RED;
        }
        else
        {
            ready = true;
            buttonFaceText = "준비하기";
            color = Color.GREEN;
        }
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button readyBtn = gtx.getView(R.id.game_ready_btn, Button.class);
                readyBtn.setText(buttonFaceText);
                readyBtn.setBackgroundColor(color);
                ParticipantListItemData participant = (ParticipantListItemData)gtx.getView(R.id.participant_list, ListView.class).getAdapter().getItem(1);
                participant.setReady(ready);
            }
        });
    }

    public void startResponse(JSONObject obj) {
        ParticipantListItemData _super =(ParticipantListItemData)gtx.getView(R.id.participant_list, ListView.class).getAdapter().getItem(0);
        ParticipantListItemData participant = (ParticipantListItemData)gtx.getView(R.id.participant_list, ListView.class).getAdapter().getItem(1);
        _super.setId(JsonUtils.get(obj, "creator").toString());
        participant.setId(JsonUtils.get(obj,"participant").toString());
        gtx.changeActivity(GameView.class, new Extras().addExtra("super", _super).addExtra("non-super", participant).addExtra("in-type","non-super").addExtra("game_no", JsonUtils.get(obj,"game_no")));
    }

    public void gameFinishWinResponse(final String me) {
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("chaser", "찾자");
                AlertDialog.Builder b = new AlertDialog.Builder(gtx.getContext());
                Log.e("chaser", "찾자2");
                b.setMessage(Html.fromHtml("<h1>승 리</h1>축하합니다. 게임에서 <strong><font color\"#0000ff\">승리</font></strong>하셨습니다.")).setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gtx.getContext().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        gtx.changeActivity(UserMainView.class, new Extras().addExtra("id", me));
                                    }
                                });
                            }
                        }).create().show();
                Log.e("chaser", "찾자3");
            }
        });

    }

    public void gameFinishLoseResponse(final String me) {
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("chaser", "찾자");
                AlertDialog.Builder b = new AlertDialog.Builder(gtx.getContext());
                Log.e("chaser", "찾자2");
                b.setMessage(Html.fromHtml("<h1>패 배</h1>게임에서 <strong><font color\"#ff0000\">패배</font></strong>하셨습니다.")).setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gtx.getContext().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        gtx.changeActivity(UserMainView.class, new Extras().addExtra("id", me));
                                    }
                                });
                            }
                        }).create().show();
                Log.e("chaser", "찾자3");
            }
        });
    }

    public void notificationGameInfo(JSONObject response) {

    }

}
