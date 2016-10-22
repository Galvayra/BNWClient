package com.example.leehyungyu.bnwgameclient.service.login;

import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.GuiUtils;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.UserMainView;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-21.
 */

public class LoginCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        GuiUtils.showToast("네트워크 연결이 원할하지 않습니다.");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        JSONObject obj = JsonUtils.parseJsonObject(response.body().string());

        Login result = Login.valueOf(JsonUtils.get(obj,"result").toString());
        if(result==Login.OK)
        {
            GuiUtils.changeActivity(UserMainView.class, new Extras().addExtra("id", JsonUtils.get(obj,"id")));
        }
        else if(result==Login.AUTH_FAIL)
        {
            GuiUtils.showToast("비밀번호를 확인하세요.");
        }
        else if(result==Login.ID_NOT_FOUND)
        {
            GuiUtils.showToast("존재하지 않는 아이디입니다.");
        }
    }
}
