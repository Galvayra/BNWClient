package com.example.leehyungyu.bnwgameclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leehyungyu.bnwgameclient.service.LoginService;
import com.example.leehyungyu.bnwgameclient.service.ResponseEventListener;
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;

import java.io.IOException;

import okhttp3.Response;

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
                JsonBuilder.addKeys("id","password");
                JsonBuilder.addValues(((EditText)findViewById(R.id.idField)).getText(),((EditText)findViewById(R.id.pwField)).getText());
                LoginService loginService = new LoginService();
                loginService.runOnBackground(JsonBuilder.build().toString());
                loginService.setOnResponse(new ResponseEventListener() {
                    @Override
                    public void onResponse(Response response) {
                        try
                        {
                            Log.d("d",response.body().string()+"");
                        }
                        catch (IOException e){}
                    }
                });
            }
        });

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
