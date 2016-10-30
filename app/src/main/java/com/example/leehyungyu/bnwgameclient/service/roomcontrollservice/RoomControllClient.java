package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import android.os.AsyncTask;
import android.util.Log;

import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;

/**
 * Created by leehyungyu on 2016-10-28.
 */

public class RoomControllClient implements WebSocketListener {

    private OkHttpClient ws;
    private int room_no;
    private String inType;

    private WebSocket wss;

    private GuiContext gtx;

    public RoomControllClient(int romm_no, String inType, GuiContext gtx) {
        this.room_no = romm_no;
        this.inType = inType;
        ws = new OkHttpClient();
        this.gtx = gtx;
    }

    public void runClient() {
        Request request = new Request.Builder().url(ServerConfiguration.ROOM_CONTROLL_SERVER).build();
        WebSocketCall wsCall = WebSocketCall.create(ws, request);
        wsCall.enqueue(this);

    }

    @Override
    public void onClose(int code, String reason) {
        Log.e("ws", "닫힘, "+reason+code);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.e("ws", "오픈");
        this.wss = webSocket;
        try
        {
            webSocket.sendMessage(RequestBody.create(WebSocket.TEXT, new JsonBuilder().addKeys("type", "room_no","in-type").addValues("enter", room_no, inType).toJsonString()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(ResponseBody message) throws IOException {
        Log.e("ws", "메세제"+message.string());
        message.close();
    }

    @Override
    public void onPong(Buffer payload) {
        Log.e("ws", "퍼ㅗ");
    }

    @Override
    public void onFailure(IOException e, Response response) {
        Log.e("ws", "실패");
    }

    public void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    wss.sendMessage(RequestBody.create(WebSocket.TEXT, new JsonBuilder().addKeys("type", "room_no","msg").addValues("chat", room_no, message).toJsonString()));
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
