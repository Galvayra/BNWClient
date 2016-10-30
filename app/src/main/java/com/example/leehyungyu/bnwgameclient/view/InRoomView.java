package com.example.leehyungyu.bnwgameclient.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import static com.example.leehyungyu.bnwgameclient.utils.JsonUtils.*;

import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.RoomControllClient;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.RoomDto;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONObject;

/**
 * Created by leehyungyu on 2016-10-26.
 */

public class InRoomView extends AppCompatActivity {

    GuiContext gtx;

    private String id;
    private String creator;
    private int no;
    private String inType;

    private ListView participantList;

    private RoomControllClient rcc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_room);
        gtx = new GuiContext(this);
        participantList = gtx.getView(R.id.participant_list, ListView.class);
        inType = getIntent().getStringExtra("in-type");

        // 방장인 경우
        if(inType.equals("super"))
        {
            Log.e("inroom", "현재 액티비티 오너의 권한은 방장입니다.");
            initSuperUser();
        }
        else if(inType.equals("non-super"))
        {
            Log.e("inroom", "현재 액티비티 오너의 권한은 참가자입니다.");
            initNonSuperUser();
        }
        Log.e("inroom","나 : "+id+", 방장 : "+creator);

        gtx.click(R.id.outOfRoom, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(gtx.getContext()).setTitle("")
                        .setMessage("방을 나가시겠습니까?")
                        .setPositiveButton("나가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
        rcc = new RoomControllClient(no, inType, gtx, id);
        rcc.runClient();

        gtx.click(R.id.send_msg_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcc.sendMessage(gtx.getTextFromView(R.id.msg_area));
            }
        });
    }

    public void initSuperUser() {
        creator = getIntent().getStringExtra("creator");
        id = getIntent().getStringExtra("id");
        no = Integer.parseInt(getIntent().getStringExtra("roomNo"));
        ((TextView)gtx.getView(R.id.roomTitle)).setText(getIntent().getStringExtra("roomTitle"));

        String[] me = {creator};
        participantList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, me));

        gtx.click(R.id.game_start_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void initNonSuperUser() {
        gtx.getView(R.id.game_start_btn,Button.class).setVisibility(Button.INVISIBLE);
        RoomDto dto = (RoomDto)getIntent().getSerializableExtra("room-info");
        ((TextView)gtx.getView(R.id.roomTitle)).setText(dto.getRoom_title());
        creator = dto.getCreator();
        id = dto.getParticipant();
        no = dto.getRoom_no();
        String[] parti = {creator, id};

        participantList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, parti));

        gtx.click(R.id.game_ready_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 방 종료 요청
    }
}
