package com.example.leehyungyu.bnwgameclient.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListItemData;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.RoomControllClient;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

/**
 * Created by leehyungyu on 2016-11-02.
 */

public class GameView extends AppCompatActivity {

    private GuiContext gtx;
    private RoomControllClient rcc;

    ParticipantListItemData _super;
    ParticipantListItemData non_super;
    private String inType;
    private int game_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);
        gtx = new GuiContext(this);

        _super = (ParticipantListItemData)getIntent().getSerializableExtra("super");
        non_super = (ParticipantListItemData)getIntent().getSerializableExtra("non-super");

        inType = getIntent().getStringExtra("in-type");  // 자신이 방장인지 참가자인지
        game_no = getIntent().getIntExtra("game_no", 0);

        _super.getId(); // 방장의 ID
        non_super.getId();  // 참가자의 ID

        rcc = RoomControllClient.getInstance();
        rcc.setGuiContext(gtx);
        rcc.setGameNumber(game_no);

        Log.e("zzzzzz", _super.getNickname()+","+game_no);
        uiInitiate();

        gtx.click(R.id.game_input_number_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberInputDialog nid = new NumberInputDialog(gtx.getContext(), "숫자 입력", rcc);
                gtx.registView("nid", nid);
                nid.show();
            }
        });

        gtx.click(R.id.game_exit_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitConfirmDialog = new AlertDialog.Builder(gtx.getContext());
                exitConfirmDialog.setTitle("경고").setMessage("게임이 끝나기전에 종료하면 패배로 기록됩니다.").setCancelable(false)
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rcc.forcedGameFinish(inType);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        gtx.getView(R.id.player_1_area, LinearLayout.class).setBackgroundResource(R.drawable.gradient_game_border_left);

        if(!inType.equals("super"))
        {
            gtx.getView(R.id.game_input_number_btn, Button.class).setEnabled(false);
        }
    }

    public void uiInitiate() {
        gtx.getView(R.id.player_1_id, TextView.class).setText(_super.getNickname());
        gtx.getView(R.id.player_2_id, TextView.class).setText(non_super.getNickname());
    }

    @Override
    public void onBackPressed() {
        gtx.showToast("게임을 끝내시려면 종료버튼을 클릭하세요.");
    }
}
