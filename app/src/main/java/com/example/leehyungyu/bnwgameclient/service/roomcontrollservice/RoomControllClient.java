package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import android.util.Log;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.ServerConfiguration;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.ParticipantResponseBundle;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.SuperResponseBundle;
import com.example.leehyungyu.bnwgameclient.utils.Extras;
import com.example.leehyungyu.bnwgameclient.utils.JsonBuilder;
import com.example.leehyungyu.bnwgameclient.utils.JsonUtils;
import com.example.leehyungyu.bnwgameclient.view.UserMainView;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private int room_no, game_no;
    private String inType;
    private String id;
    private WebSocket wss;

    private GuiContext gtx;

    private SuperResponseBundle superResponseBundle;
    private ParticipantResponseBundle participantResponseBundle;

    private static RoomControllClient instance = null;

    private RoomControllClient(int romm_no, String inType, GuiContext gtx, String id) {
        this.room_no = romm_no;
        this.inType = inType;
        this.id = id;
        this.gtx = gtx;
        ws = new OkHttpClient.Builder().connectTimeout(0, TimeUnit.MILLISECONDS).readTimeout(0, TimeUnit.MILLISECONDS).writeTimeout(0, TimeUnit.MILLISECONDS).build();
        superResponseBundle = new SuperResponseBundle(gtx);
        participantResponseBundle = new ParticipantResponseBundle(gtx);
    }

    public static void createInstance(int _romm_no, String _inType, GuiContext _gtx, String _id) {
        instance = new RoomControllClient(_romm_no, _inType, _gtx, _id);
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
            wss.sendMessage(RequestBody.create(WebSocket.TEXT, new JsonBuilder().addKeys("type", "room_no","in-type").addValues("enter", room_no, inType).toJsonString()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(ResponseBody message) throws IOException {
        Log.e("ws", "메세지 수신");
        JSONObject obj = JsonUtils.parseJsonObject(message.string());
        String type = JsonUtils.get(obj, "type").toString();
        try
        {
            if(type.equals("chat"))
            {
                gtx.appendText(R.id.chat_area, "[ "+JsonUtils.get(obj,"speaker")+" ] "+JsonUtils.get(obj, "msg").toString());
                scrollBottom(gtx.getView(R.id.chat_area, TextView.class));
            }
            else if(type.equals("game_start_operation"))
            {
                if(inType.equals("super"))
                {
                    superResponseBundle.startResponse(obj);
                }
                else if(inType.equals("non-super"))
                {
                    participantResponseBundle.startResponse(obj);
                }
            }
            else if(type.equals("ready_result"))
            {
                participantResponseBundle.readyResponse(obj);
            }
            else if(type.equals("notify_new_participant"))
            {
                superResponseBundle.notifyParticipantEnter(obj);
            }
            else if(type.equals("ready_notify"))
            {
                superResponseBundle.notifyParticipantReady(obj);
            }
            else if(type.equals("out!"))
            {
                Log.e("out-test", inType+"방을 나갑니다.");
                wss.close(1000, "out");
                gtx.changeActivity(UserMainView.class, new Extras().addExtra("id", id));
            }
            else if(type.equals("participant_out!"))
            {
                superResponseBundle.notifyParticipantOut();
            }
            else if(type.equals("game_finish_out_win"))
            {
                if(inType.equals("super"))
                {
                    superResponseBundle.gameFinishWinResponse(id);
                }
                else if(inType.equals("non-super"))
                {
                    participantResponseBundle.gameFinishWinResponse(id);
                }
            }
            else if(type.equals("game_finish_out_lose"))
            {
                if(inType.equals("super"))
                {
                    superResponseBundle.gameFinishLoseResponse(id);
                }
                else if(inType.equals("non-super"))
                {
                    participantResponseBundle.gameFinishLoseResponse(id);
                }
            }
            else if(type.equals("notification_game_info"))
            {
                if(inType.equals("super"))
                {
                    superResponseBundle.notificationGameInfo(obj);
                }
                else if(inType.equals("non-super"))
                {
                    participantResponseBundle.notificationGameInfo(obj);
                }
            }
        }
        catch(JSONException e)
        {
            gtx.showToast("서버와 통신중에 문제가 발생했습니다.");
            e.printStackTrace();
        }

    }

    private void scrollBottom(TextView textView) {
        int lineTop =  textView.getLayout().getLineTop(textView.getLineCount()) ;
        int scrollY = lineTop - textView.getHeight();
        if (scrollY > 0)
        {
            textView.scrollTo(0, scrollY);
        }
        else
        {
            textView.scrollTo(0, 0);
        }
    }

    @Override
    public void onPong(Buffer payload) {
        Log.e("ws", "퍼ㅗ");
    }

    @Override
    public void onFailure(IOException e, Response response) {
        e.printStackTrace();
        Log.e("ws", "실패");
    }

    public void setGameNumber(int gameNo) {
        this.game_no = gameNo;
    }

    public void setGuiContext(GuiContext gtx) {
        this.gtx = gtx;
        superResponseBundle.setGuiContext(gtx);
        participantResponseBundle.setGuiContext(gtx);
    }

    public void sendMessage(final String message) {
        JSONObject messageRequest = new JsonBuilder().addKeys("type", "room_no","msg","id").addValues("chat", room_no, message, id).build();
        sendJsonRequest(messageRequest);
    }

    public void gameStart() {
        JSONObject gameStartRequest = new JsonBuilder().addKeys("type","room_no").addValues("game_start", room_no).build();
        sendJsonRequest(gameStartRequest);
    }

    public void sendJsonRequest(final JSONObject requestJson) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    wss.sendMessage(RequestBody.create(WebSocket.TEXT, requestJson.toString()));
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void ready() {
        JSONObject readyRequest = new JsonBuilder().addKeys("type", "room_no").addValues("participant_ready", room_no).build();
        sendJsonRequest(readyRequest);
    }

    public void readyCancel() {
        JSONObject readyRequest = new JsonBuilder().addKeys("type", "room_no").addValues("participant_ready_cancel", room_no).build();
        sendJsonRequest(readyRequest);
    }

    public void outOfRoom() {
        JSONObject outRequest = new JsonBuilder().addKeys("type", "room_no", "in_type").addValues("out_of_room", room_no, inType).build();
        sendJsonRequest(outRequest);
    }

    public static RoomControllClient getInstance() {
        return instance;
    }

    public void forcedGameFinish(String inType) {
        JSONObject outRequest = new JsonBuilder().addKeys("type", "room_no", "in_type", "game_no").addValues("forced_game_finish", room_no, inType, game_no).build();
        sendJsonRequest(outRequest);
    }

    public void endMyTurn(int score) {
        JSONObject outRequest = new JsonBuilder().addKeys("type", "room_no", "in_type", "game_no", "score").addValues("end_my_turn", room_no, inType, game_no, score).build();
        sendJsonRequest(outRequest);
    }
}
