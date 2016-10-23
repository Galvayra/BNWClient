package com.example.leehyungyu.bnwgameclient.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.login.LoginService;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

public class MainView extends AppCompatActivity {

    private GuiContext gtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gtx = new GuiContext(this);

        checkAutoLogin();

        gtx.click(R.id.loginBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = gtx.getTextFromView(R.id.idField);
                String password = gtx.getTextFromView(R.id.pwField);
                new LoginService(gtx).runOnBackground(id, password);
            }
        });

        gtx.click(R.id.register, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gtx.showToast("회원가입 화면으로 이동");
            }
        });
    }

    public void checkAutoLogin() {
        SharedPreferences prefs = getSharedPreferences("bnw-pref", MODE_PRIVATE);
        if(prefs!=null)
        {
            if(prefs.getBoolean("auto-login", false))
            {
                String id = prefs.getString("id","");
                String password = prefs.getString("password","");
                new LoginService(gtx).runOnBackground(id, password);
            }
            else
            {
                gtx.showToast("자동 로그인 설정된 것 없음");
            }
        }
        else
        {
            gtx.showToast("설정된 preference 없음");
        }
    }

}
