package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import android.util.Log;

import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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

    public RoomControllClient() {
        ws = new OkHttpClient();
    }

    public void runClient() {
        Request request = new Request.Builder().url(ServerConfiguration.ROOM_CONTROLL_SERVER).build();
        WebSocketCall wsCall = WebSocketCall.create(ws, request);
        wsCall.enqueue(this);
    }

    @Override
    public void onClose(int code, String reason) {
        Log.e("ws", "닫힘, "+reason);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.e("ws", "오픈");
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
}
