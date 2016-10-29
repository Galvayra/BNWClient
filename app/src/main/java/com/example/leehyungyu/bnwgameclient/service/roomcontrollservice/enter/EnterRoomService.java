package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.enter;

import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.RoomDto;
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

public class EnterRoomService extends Service {

    public EnterRoomService(GuiContext gtx, String serviceOwner) {
        super(gtx, serviceOwner);
    }

    @Override
    protected Callback buildCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200)
                {
                    JSONObject oj = JsonUtils.parseJsonObject(response.body().string());
                    getGuiContext().changeActivity(InRoomView.class, new Extras().addExtra("room-info", JsonUtils.mappingObject((JSONObject)JsonUtils.get(oj, "room"), RoomDto.class)).addExtra("in-type","non-super"));
                }
                else
                {
                    getGuiContext().showToast("서버와의 통신이 원할하지 않습니다.");
                }

            }
        };
    }

    @Override
    protected Request buildRequest(Object... param) {
        RequestBody body = jsonRequestBody(new JsonBuilder().addKeys("room_no", "participant").addValues(param[0], getServiceOwner()).toJsonString());
        return new Request.Builder().url(ServerConfiguration.ENTER_GAME_ROOM_REQUEST).post(body).build();
    }
}
