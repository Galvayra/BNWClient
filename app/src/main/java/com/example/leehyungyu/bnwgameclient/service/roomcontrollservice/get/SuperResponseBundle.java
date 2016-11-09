package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListAdapter;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListItemData;
import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.GameView;
import com.example.leehyungyu.bnwgameclient.view.UserMainView;
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

    public void setGuiContext(GuiContext gtx) {
        this.gtx = gtx;
    }

    public void startResponse(JSONObject obj) {
        boolean response = (boolean) JsonUtils.get(obj, "result");
        if(response)
        {
            ParticipantListItemData _super =(ParticipantListItemData)gtx.getView(R.id.participant_list, ListView.class).getAdapter().getItem(0);
            ParticipantListItemData participant = (ParticipantListItemData)gtx.getView(R.id.participant_list, ListView.class).getAdapter().getItem(1);
            gtx.changeActivity(GameView.class, new Extras().addExtra("super", _super).addExtra("non-super", participant).addExtra("in-type","super").addExtra("game_no", JsonUtils.get(obj,"game_no")));
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

        final ParticipantListAdapter adapter = (ParticipantListAdapter)gtx.getView(R.id.participant_list, ListView.class).getAdapter();
        ParticipantListItemData itemData = (ParticipantListItemData)adapter.getItem(1);

        if(result.equals("ready"))
        {
            itemData.setReady(true);
            notifyString = "[ SYSTEM ] 플레이어가 준비 완료했습니다. 게임을 시작할 수 있습니다.";
        }
        else
        {
            itemData.setReady(false);
            notifyString = "[ SYSTEM ] 플레이어 준비 취소";
        }
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gtx.appendText(R.id.chat_area, notifyString);
                adapter.notifyDataSetChanged();
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
        String turn = JsonUtils.get(response, "turn").toString();

        JSONObject gameObject = (JSONObject)JsonUtils.get(response, "game");

        if(turn.equals("super"))
        {
            gtx.getView(R.id.game_input_number_btn, Button.class).setEnabled(true);
        }



    }
}
