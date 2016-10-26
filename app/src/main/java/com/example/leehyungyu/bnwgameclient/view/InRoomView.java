package com.example.leehyungyu.bnwgameclient.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

/**
 * Created by leehyungyu on 2016-10-26.
 */

public class InRoomView extends AppCompatActivity {

    GuiContext gtx;

    private String id;
    private String creator;
    private int no;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_room);
        gtx = new GuiContext(this);

        creator = getIntent().getStringExtra("creator");
        id = getIntent().getStringExtra("id");
        no = Integer.parseInt(getIntent().getStringExtra("roomNo"));

        Log.e("inroom","나 : "+id+", 방장 : "+creator);

        ((TextView)gtx.getView(R.id.roomTitle)).setText(getIntent().getStringExtra("roomTitle"));

        gtx.click(R.id.outOfRoom, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(gtx.getContext()).setTitle("")
                        .setMessage("")
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 방 종료 요청
    }
}
