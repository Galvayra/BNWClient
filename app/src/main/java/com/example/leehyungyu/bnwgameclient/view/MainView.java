package com.example.leehyungyu.bnwgameclient.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.login.Login;
import com.example.leehyungyu.bnwgameclient.service.login.LoginService;
import com.example.leehyungyu.bnwgameclient.service.callback.JsonResponseCallback;
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
                String id = ((EditText)findViewById(R.id.idField)).getText().toString();
                String password = ((EditText)findViewById(R.id.pwField)).getText().toString();

                LoginService loginService = new LoginService();
                loginService.runOnBackground(id, password);

                loginService.setOnResponse(new JsonResponseCallback() {
                    @Override
                    public void onJsonResponse(JSONObject responseObject) throws JSONException {
                        Login result = Login.valueOf(JsonUtils.get(responseObject,"result").toString());
                        if(result==Login.OK)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent userMainViewIntent = new Intent(getApplicationContext(), UserMainView.class);
                                    startActivity(userMainViewIntent);
                                }
                            });
                        }
                        else if(result==Login.AUTH_FAIL)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해 주세요.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        else if(result==Login.ID_NOT_FOUND)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent userMainViewIntent = new Intent(getApplicationContext(), UserMainView.class);
//                startActivity(userMainViewIntent);
            }
        });
    }



}
