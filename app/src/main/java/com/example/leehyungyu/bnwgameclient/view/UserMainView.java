package com.example.leehyungyu.bnwgameclient.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.mypageservice.MyPageService;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.create.CreateRoomService;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.GetRoomListService;
import com.example.leehyungyu.bnwgameclient.view.gui.ChangableGuiContext;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class UserMainView extends AppCompatActivity {

    private ListView drawer;
    private ChangableGuiContext gtx;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        userId = getIntent().getStringExtra("id");
        guiInit();
        gtx.showToast("환영합니다. "+userId+"으로 접속하셨습니다.");
    }

    public void guiInit() {
        gtx = new ChangableGuiContext(this);
        gtx.registScene(R.id.contextView_my_page);
        gtx.registScene(R.id.contextView_second);
        gtx.registScene(R.id.contextView_third);

        drawer = gtx.getView(R.id.drawer, ListView.class);

        String[] listContent = {"마이페이지","게임참가","세번째 메뉴?"};
         /* 숨김 메뉴 아답터 설정, 이벤트 설정 */
        drawer.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listContent));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0 :
                        /* myPage 정보 요청 서비스 */
                        new MyPageService(gtx, userId).runOnBackground(userId);
                        gtx.show(R.id.contextView_my_page);
                        break;
                    case 1 :
                        /* 방목록 요청 서비스 */
                        new GetRoomListService(gtx, userId).runOnBackground(userId);
                        gtx.show(R.id.contextView_second);
                        break;
                    case 2 :
                        gtx.show(R.id.contextView_third);
                        break;
                }
            }
        });

        /* 갱신버튼 클릭시 */
        gtx.click(R.id.refresh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetRoomListService(gtx, userId).runOnBackground();
            }
        });

        /* 방 생성 버튼 클릭시 */
        gtx.click(R.id.createRoomBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(gtx.getContext());
                dlgBuilder.setTitle("새로운 방 만들기");
                dlgBuilder.setMessage("방 제목을 입력하세요");
                final EditText input = new EditText(gtx.getContext());
                dlgBuilder.setView(input);
                dlgBuilder.setPositiveButton("만들기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = input.getText().toString();
                        new CreateRoomService(gtx, userId).runOnBackground(userId, title);
                        dialog.dismiss();
                    }
                });
                dlgBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dlgBuilder.show();
            }
        });
        new GetRoomListService(gtx, userId).runOnBackground(userId);
        gtx.show(R.id.contextView_second);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Thread service = gtx.getServiceManager().getService(R.string.refresher_service);
        if(service!=null)
        {
            service.interrupt();
        }
    }
}
