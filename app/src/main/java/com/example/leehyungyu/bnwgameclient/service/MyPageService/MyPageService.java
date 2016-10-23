package com.example.leehyungyu.bnwgameclient.service.MyPageService;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import static com.example.leehyungyu.bnwgameclient.utils.JsonUtils.*;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-22.
 */

public class MyPageService extends Service {

    SharedPreferences pref = getGuiContext().getContext().getSharedPreferences("bnw-pref", Context.MODE_PRIVATE);

    public MyPageService(GuiContext guiContext) {
        super(guiContext);
    }

    @Override
    protected Request buildRequest(Object... param) {
        JSONObject jo = new JsonBuilder().addKeys("id").addValues(param[0]).build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jo.toString());
        Request request = new Request.Builder().post(body).url(ServerConfiguration.MYPAGE_VIEW_REQUEST).build();
        return request;
    }

    @Override
    protected Callback buildCallback(Object... param) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getGuiContext().showToast(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject res = parseJsonObject(response.body().string());

                final JSONObject user = (JSONObject)get(res, "user");
                final JSONObject record = (JSONObject)get(res, "record");
                final TextView nickname = getGuiContext().getView(R.id.nickname, TextView.class);
                final TextView email = getGuiContext().getView(R.id.email, TextView.class);
                final TextView creatDate = getGuiContext().getView(R.id.create_date, TextView.class);
                final TextView win = getGuiContext().getView(R.id.win, TextView.class);
                final TextView draw = getGuiContext().getView(R.id.draw, TextView.class);
                final TextView lose = getGuiContext().getView(R.id.lose, TextView.class);

                final Switch auto_login = getGuiContext().getView(R.id.auto_login, Switch.class);

                getGuiContext().getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nickname.setText(get(user,"nickname").toString());
                        email.setText(get(user,"email").toString());
                        creatDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(get(user,"create_date").toString()))));
                        win.setText(get(record,"win").toString());
                        draw.setText(get(record,"draw").toString());
                        lose.setText(get(record,"lose").toString());

                        if(pref.getBoolean("auto-login", false))
                        {
                            auto_login.setChecked(true);
                        }
                        else
                        {
                            auto_login.setChecked(false);
                        }
                    }
                });

                getGuiContext().click(R.id.auto_login, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isChecked = ((Switch)v).isChecked();

                        SharedPreferences.Editor editor = pref.edit();

                        if(!isChecked)
                        {
                            editor.putBoolean("auto-login", false);
                            getGuiContext().showToast("자동로그인 기능이 해제됐습니다.");
                        }
                        else
                        {
                            editor.putBoolean("auto-login", true);
                            getGuiContext().showToast("자동로그인 기능이 설정됐습니다.");
                        }
                        editor.apply();
                    }
                });
            }
        };
    }
}
