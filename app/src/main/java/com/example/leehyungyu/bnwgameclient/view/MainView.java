package com.example.leehyungyu.bnwgameclient.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.login.LoginService;
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;

public class MainView extends AppCompatActivity {

    Button loginBtn;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GuiUtils.setContext(this);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = GuiUtils.getTextFromView(R.id.idField);
                String password = GuiUtils.getTextFromView(R.id.pwField);

                LoginService loginService = new LoginService();
                loginService.runOnBackground(id, password);
            }
        });

        register = GuiUtils.getView(R.id.register, TextView.class);
        register.setText(R.string.link_for_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuiUtils.showToast("회원가입화면으로 이동");
            }
        });
    }



}
