package com.example.leehyungyu.bnwgameclient.view;

 import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leehyungyu.bnwgameclient.R;
 import com.example.leehyungyu.bnwgameclient.service.login.LoginService;
 import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;

public class MainView extends AppCompatActivity {

    Button loginBtn, registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GuiUtils.setContext(this);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registBtn = (Button) findViewById(R.id.registBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = GuiUtils.getTextFromView(R.id.idField);
                String password = GuiUtils.getTextFromView(R.id.pwField);

                LoginService loginService = new LoginService();
                loginService.runOnBackground(id, password);
            }
        });

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }



}
