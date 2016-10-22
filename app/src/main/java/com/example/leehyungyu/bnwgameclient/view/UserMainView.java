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
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class UserMainView extends AppCompatActivity {

    private RelativeLayout myPageView, secondView, thirdView;
    private ListView drawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        guiInit();

    }

    public void guiInit() {
        GuiUtils.setContext(this);
        myPageView = GuiUtils.getView(R.id.contextView_my_page, RelativeLayout.class);
        secondView = GuiUtils.getView(R.id.contextView_second, RelativeLayout.class);
        thirdView = GuiUtils.getView(R.id.contextView_third, RelativeLayout.class);
        drawer = GuiUtils.getView(R.id.drawer,ListView.class);

        String[] listContent = {"MyPage","B","C"};
        drawer.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listContent));

        drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0 :
                        GuiUtils.showToast("MyPage로 전환");
                        new MyPageService().runOnBackground(getIntent().getStringExtra("id"));
                        myPageView.setVisibility(RelativeLayout.VISIBLE);
                        secondView.setVisibility(RelativeLayout.INVISIBLE);
                        thirdView.setVisibility(RelativeLayout.INVISIBLE);
                        break;
                    case 1 :
                        GuiUtils.showToast("두번째로 전환");
                        myPageView.setVisibility(RelativeLayout.INVISIBLE);
                        secondView.setVisibility(RelativeLayout.VISIBLE);
                        thirdView.setVisibility(RelativeLayout.INVISIBLE);
                        break;
                    case 2 :
                        GuiUtils.showToast("세번째로 전환");
                        myPageView.setVisibility(RelativeLayout.INVISIBLE);
                        secondView.setVisibility(RelativeLayout.INVISIBLE);
                        thirdView.setVisibility(RelativeLayout.VISIBLE);
                        break;
                }
            }
        });
    }


}
