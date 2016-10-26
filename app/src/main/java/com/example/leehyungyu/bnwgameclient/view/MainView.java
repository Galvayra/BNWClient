package com.example.leehyungyu.bnwgameclient.view;

import android.content.SharedPreferences;
import android.os.Handler;
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
        guiInit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        guiInit();
    }

    public void guiInit() {
        final String id = gtx.getTextFromView(R.id.idField);
        gtx.click(R.id.loginBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoginService loginService = new LoginService(gtx, id);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String password = gtx.getTextFromView(R.id.pwField);
                        loginService.runOnBackground(id, password);
                    }
                }, 1500);
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
        final SharedPreferences prefs = getSharedPreferences("bnw-pref", MODE_PRIVATE);

        if(prefs!=null)
        {
            if(prefs.getBoolean("auto-login", false))
            {
                final String id = prefs.getString("id","");
                final LoginService loginService = new LoginService(gtx, id);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String password = prefs.getString("password","");
                        loginService.runOnBackground(id, password);
                    }
                }, 1500);
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
