package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.create;

import android.util.Log;

import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.InRoomView;
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
 * Created by leehyungyu on 2016-10-26.
 */

public class CreateRoomService extends Service {

    public CreateRoomService(GuiContext context, String serviceOwner) {
        super(context, serviceOwner);
    }

    @Override
    protected Request buildRequest(Object... param) {
        JsonBuilder builder = new JsonBuilder().addKeys("id", "title").addValues(getServiceOwner(), param[1]);
        return new Request.Builder().post(jsonRequestBody(builder.toJsonString())).url(ServerConfiguration.CREATE_GAME_ROOM_REQUEST).build();
    }

    @Override
    protected Callback buildCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("createRoomFail", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.e("createRoomSuccess", json);
                if(response.code()==200)
                {
                    JSONObject jo = JsonUtils.parseJsonObject(json);
                    getGuiContext().changeActivity(InRoomView.class, new Extras()
                            .addExtra("creator", JsonUtils.get(jo,"creator"))
                            .addExtra("id", getServiceOwner())
                            .addExtra("roomTitle", JsonUtils.get(jo,"roomTitle"))
                            .addExtra("roomNo", JsonUtils.get(jo,"roomNo")+""));

                }
                else
                {
                    getGuiContext().showToast("방 생성 도중에 문제가 발생했습니다.");
                }
            }
        };
    }
}
