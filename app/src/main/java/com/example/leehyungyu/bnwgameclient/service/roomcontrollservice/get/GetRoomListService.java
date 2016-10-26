package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.background.LocalServiceManager;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.Service;
import static com.example.leehyungyu.bnwgameclient.utils.JsonUtils.*;

import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.enter.EnterRoomService;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class GetRoomListService extends Service {

    public GetRoomListService(GuiContext gtx, String serviceOwner) {
        super(gtx, serviceOwner);
        final ProgressDialog pDlg = gtx.getView(gtx.findString(R.string.loadgin_dialog),ProgressDialog.class);
        if(pDlg==null)
        {
            gtx.registView(gtx.findString(R.string.loadgin_dialog), ProgressDialog.show(gtx.getContext(), "", "새로운 방목록을 가져오고 있습니다.", true));
        }
        else if(pDlg.isShowing()==false)
        {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDlg.show();
                }
            },0);
            gtx.registView(gtx.findString(R.string.loadgin_dialog), pDlg);
        }
    }

    @Override
    protected Callback buildCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getGuiContext().showToast("서버와 통신이 원할하지 않습니다.");
                Thread service = getGuiContext().getServiceManager().getService(R.string.refresher_service);
                if(service!=null)
                {
                    service.interrupt();
                }
                getGuiContext().getView(getGuiContext().findString(R.string.loadgin_dialog), ProgressDialog.class).dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200)
                {
                    JSONObject obj = parseJsonObject(response.body().string());
                    JSONArray roomsJson = (JSONArray)get(obj,"rooms");

                    getGuiContext().setListAdapter(R.id.roomList, new RoomListAdapter(getGuiContext().getContext(), roomsJson));
                    getGuiContext().setListItemClick(R.id.roomList, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            new EnterRoomService(getGuiContext(), getServiceOwner()).runOnBackground(((TextView)view.findViewById(R.id.room_no)).getText().toString());
                        }
                    });

                    LocalServiceManager manager = getGuiContext().getServiceManager();
                    getGuiContext().getContext().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)getGuiContext().getView(R.id.recentRefreshTime)).setText(new SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(new Date(System.currentTimeMillis())));
                        }
                    });
                    if(manager.getService(R.string.refresher_service)==null||!manager.getService(R.string.refresher_service).isAlive())
                    {
                        Log.e("service", "새로고침 스레드 활성화");
                        manager.registService(R.string.refresher_service, new Refresher(getGuiContext(), 15000, getServiceOwner()));
                        manager.getService(R.string.refresher_service).start();
                    }
                }
                else
                {
                    getGuiContext().showToast("서버와 통신이 원할하지 않습니다.");
                }
                getGuiContext().getView(getGuiContext().findString(R.string.loadgin_dialog), ProgressDialog.class).dismiss();
            }
        };
    }

    @Override
    protected Request buildRequest(Object... param) {
        Request request = new Request.Builder().get().url(ServerConfiguration.GET_GAME_ROOM_REQUEST).build();
        return request;
    }
}
