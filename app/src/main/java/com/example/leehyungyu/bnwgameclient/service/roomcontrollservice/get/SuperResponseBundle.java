package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
        final String turn = JsonUtils.get(response, "turn").toString();
        Log.e("asdnaskdmkajs", response.toString());
        final JSONObject gameObject = JsonUtils.parseJsonObject(JsonUtils.get(response,"game").toString());
        Log.e("asdnaskdmkajs", JsonUtils.get(response,"game").toString());

        Log.e("asd", gameObject.toString());

        final int gamer_1_score = (Integer)JsonUtils.get(gameObject, "gamer_1_score");
        final int gamer_2_score = (Integer)JsonUtils.get(gameObject, "gamer_2_score");

        final int score = (Integer)JsonUtils.get(response, "score");

        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Log.e("score", gamer_1_score+"");
                Log.e("score", gamer_2_score+"");
                Log.e("turn", turn);

                if(turn.equals("super"))
                {
                    gtx.getView(R.id.game_input_number_btn, Button.class).setEnabled(true);
                    gtx.getView(R.id.player_1_area, LinearLayout.class).setBackgroundResource(R.drawable.gradient_game_border_left);
                    gtx.getView(R.id.player_2_area, LinearLayout.class).setBackgroundResource(R.drawable.null_background);
                }
                else
                {
                    gtx.getView(R.id.game_input_number_btn, Button.class).setEnabled(false);
                    gtx.getView(R.id.player_2_area, LinearLayout.class).setBackgroundResource(R.drawable.gradient_game_border_right);
                    gtx.getView(R.id.player_1_area, LinearLayout.class).setBackgroundResource(R.drawable.null_background);
                }

                if(score>=10)
                {
                    gtx.getView(R.id.current_turn, TextView.class).setBackgroundColor(Color.WHITE);
                }
                else
                {
                    gtx.getView(R.id.current_turn, TextView.class).setBackgroundColor(Color.BLACK);
                }
                TextView player_1_bar80 = gtx.getView(R.id.player_1_status_80, TextView.class);
                TextView player_1_bar60 = gtx.getView(R.id.player_1_status_60, TextView.class);
                TextView player_1_bar40 = gtx.getView(R.id.player_1_status_40, TextView.class);
                TextView player_1_bar20 = gtx.getView(R.id.player_1_status_20, TextView.class);

                TextView player_2_bar80 = gtx.getView(R.id.player_2_status_80, TextView.class);
                TextView player_2_bar60 = gtx.getView(R.id.player_2_status_60, TextView.class);
                TextView player_2_bar40 = gtx.getView(R.id.player_2_status_40, TextView.class);
                TextView player_2_bar20 = gtx.getView(R.id.player_2_status_20, TextView.class);

                if (gamer_1_score < 80 && gamer_1_score>=60) {
                    player_1_bar80.setText("");
                    player_1_bar80.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_1_score < 60 && gamer_1_score>=40) {
                    player_1_bar60.setText("");
                    player_1_bar60.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_1_score < 40 && gamer_1_score>=20) {
                    player_1_bar40.setText("");
                    player_1_bar40.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_1_score < 20) {
                    player_1_bar20.setText("");
                    player_1_bar20.setBackgroundResource(R.drawable.null_background);
                }

                if (gamer_2_score < 80 && gamer_2_score>=60) {
                    player_2_bar80.setText("");
                    player_2_bar80.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_2_score < 60 && gamer_2_score>=40) {
                    player_2_bar60.setText("");
                    player_2_bar60.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_2_score < 40 && gamer_2_score>=20) {
                    player_2_bar40.setText("");
                    player_2_bar40.setBackgroundResource(R.drawable.null_background);
                } else if (gamer_2_score < 20) {
                    player_2_bar20.setText("");
                    player_2_bar20.setBackgroundResource(R.drawable.null_background);
                }

                gtx.getView(R.id.player_1_status, TextView.class).setText(JsonUtils.get(gameObject, "gamer_1_round").toString());
                gtx.getView(R.id.player_2_status, TextView.class).setText(JsonUtils.get(gameObject, "gamer_2_round").toString());
            }
        });
    }

    public void notificationNotInvalidNumber() {
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gtx.showToast("자신의 현재점수보다 큰 숫자를 낼 수 없습니다.");
            }
        });
    }

    public void draw(final String me) {
        gtx.getContext().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder b = new AlertDialog.Builder(gtx.getContext());
                b.setMessage(Html.fromHtml("<h1>무 승 부</h1>게임이 무승부로 끝났습니다.")).setCancelable(false)
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
            }
        });
    }
}
