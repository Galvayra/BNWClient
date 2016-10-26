package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.util.Log;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.background.LocalServiceManager;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import static com.example.leehyungyu.bnwgameclient.utils.JsonUtils.*;

import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class GetRoomListService extends Service {

    public GetRoomListService(GuiContext gtx) {
        super(gtx);
    }

    @Override
    protected Callback buildCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getGuiContext().showToast("Fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject obj = parseJsonObject(response.body().string());
                JSONArray roomsJson = (JSONArray)get(obj,"rooms");

                getGuiContext().setListAdapter(R.id.roomList, new RoomListAdapter(getGuiContext().getContext(), roomsJson));
                LocalServiceManager manager = getGuiContext().getServiceManager();
                getGuiContext().getContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)getGuiContext().getView(R.id.recentRefreshTime)).setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                    }
                });
                if(manager.getService("refresher")==null||!manager.getService("refresher").isAlive())
                {
                    Log.e("service", "새로고침 스레드 활성화");
                    manager.registService("refresher", new Refresher(getGuiContext(), 15000));
                    manager.getService("refresher").start();
                }
            }
        };
    }

    @Override
    protected Request buildRequest(Object... param) {
        Request request = new Request.Builder().get().url(ServerConfiguration.GET_GAME_ROOM_REQUEST).build();
        return request;
    }
}
