package com.example.leehyungyu.bnwgameclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.ParticipantListItemData;
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

        ParticipantListItemData _super = (ParticipantListItemData)getIntent().getSerializableExtra("super");
        ParticipantListItemData non_super = (ParticipantListItemData)getIntent().getSerializableExtra("non-super");

        String inType = getIntent().getStringExtra("in-type");  // 자신이 방장인지 참가자인지

        _super.getId(); // 방장의 ID
        non_super.getId();  // 참가자의 ID
        Log.e("e",_super.getId()+","+non_super.getId()+","+inType);
        gtx.click(R.id.game_exit_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gtx.getView(R.id.player_1_area, LinearLayout.class).setBackgroundResource(R.drawable.gradient_game_border_left);

    }

    @Override
    public void onBackPressed() {
        gtx.showToast("게임을 끝내시려면 종료버튼을 클릭하세요.");
    }
}
