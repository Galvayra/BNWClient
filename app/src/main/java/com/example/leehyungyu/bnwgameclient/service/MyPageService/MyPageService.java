package com.example.leehyungyu.bnwgameclient.service.MyPageService;

import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
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



    @Override
    protected Request buildRequest(Object... param) {
        JSONObject jo = new JsonBuilder().addKeys("id").addValues(param[0]).build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jo.toString());
        Request request = new Request.Builder().post(body).url(ServerConfiguration.MYPAGE_VIEW_REQUEST).build();

        useCallback(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                GuiUtils.showToast(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject res = parseJsonObject(response.body().string());
                final JSONObject user = (JSONObject)get(res, "user");
                final JSONObject record = (JSONObject)get(res, "record");
                final TextView nickname = GuiUtils.getView(R.id.nickname, TextView.class);
                final TextView email = GuiUtils.getView(R.id.email, TextView.class);
                final TextView creatDate = GuiUtils.getView(R.id.create_date, TextView.class);
                final TextView win = GuiUtils.getView(R.id.win, TextView.class);
                final TextView draw = GuiUtils.getView(R.id.draw, TextView.class);
                final TextView lose = GuiUtils.getView(R.id.lose, TextView.class);

                GuiUtils.getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nickname.setText(get(user,"nickname").toString());
                        email.setText(get(user,"email").toString());
                        creatDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(get(user,"create_date").toString()))));
                        win.setText(get(record,"win").toString());
                        draw.setText(get(record,"draw").toString());
                        lose.setText(get(record,"lose").toString());
                    }
                });
            }
        });

        return request;
    }
}
