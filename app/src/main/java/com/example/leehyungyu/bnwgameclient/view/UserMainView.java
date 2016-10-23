package com.example.leehyungyu.bnwgameclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.MyPageService.MyPageService;
import com.example.leehyungyu.bnwgameclient.view.gui.ChangableGuiContext;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class UserMainView extends AppCompatActivity {

    private ListView drawer;

    private ChangableGuiContext gtx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        guiInit();
    }

    public void guiInit() {
        gtx = new ChangableGuiContext(this);
        gtx.registScene(R.id.contextView_my_page);
        gtx.registScene(R.id.contextView_second);
        gtx.registScene(R.id.contextView_third);

        drawer = gtx.getView(R.id.drawer, ListView.class);

        String[] listContent = {"마이페이지","게임참가","세번째 메뉴?"};
        drawer.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listContent));
        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0 :
                        new MyPageService(gtx).runOnBackground(getIntent().getStringExtra("id"));
                        gtx.show(R.id.contextView_my_page);
                        break;
                    case 1 :
                        gtx.show(R.id.contextView_second);
                        break;
                    case 2 :
                        gtx.show(R.id.contextView_third);
                        break;
                }
            }
        });
    }


}
