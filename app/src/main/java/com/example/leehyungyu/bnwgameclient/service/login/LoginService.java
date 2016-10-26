package com.example.leehyungyu.bnwgameclient.service.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.CheckBox;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.UserMainView;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class LoginService extends Service {

    public LoginService(GuiContext gtx) {
        super(gtx);
        ProgressDialog pDlg = ProgressDialog.show(gtx.getContext(), "", "로그인 중입니다.", true);
        gtx.registView("login-dlg", pDlg);
    }

    @Override
    protected Request buildRequest(Object... param) {

        JsonBuilder jb = new JsonBuilder();
        jb.addKeys("id","password").addValues(param[0], param[1]);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jb.toJsonString());
        Request request = new Request.Builder().url(ServerConfiguration.LOGIN_REQUEST_URI).post(body).build();

        return request;
    }

    @Override
    protected Callback buildCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final ProgressDialog pDlg = getGuiContext().getView("login-dlg", ProgressDialog.class);
                if(pDlg!=null) {
                    getGuiContext().getContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pDlg.dismiss();
                            getGuiContext().showToast("네트워크 연결이 원할하지 않습니다.");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final JSONObject obj = JsonUtils.parseJsonObject(response.body().string());

                Login result = Login.valueOf(JsonUtils.get(obj,"result").toString());
                if(result==Login.OK)
                {
                    if(getGuiContext().getView(R.id.auto_login_usable, CheckBox.class).isChecked())
                    {
                        saveUserInstance(obj);
                    }
                    getGuiContext().getContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getGuiContext().getView("login-dlg", ProgressDialog.class).dismiss();
                            getGuiContext().changeActivity(UserMainView.class, new Extras().addExtra("id", JsonUtils.get(obj,"id")));
                        }
                    });
                }
                else if(result==Login.AUTH_FAIL)
                {
                    getGuiContext().getView("login-dlg", ProgressDialog.class).dismiss();
                    getGuiContext().showToast("비밀번호를 확인하세요.");
                }
                else if(result==Login.ID_NOT_FOUND)
                {
                    getGuiContext().getView("login-dlg", ProgressDialog.class).dismiss();
                    getGuiContext().showToast("존재하지 않는 아이디입니다.");
                }

            }
        };
    }

    public void saveUserInstance(JSONObject object) {
        SharedPreferences prefs = getGuiContext().getContext().getSharedPreferences("bnw-pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("auto-login", true);
        editor.putString("id", JsonUtils.get(object, "id").toString());
        editor.putString("password", JsonUtils.get(object, "password").toString());
        editor.commit();
    }
}
