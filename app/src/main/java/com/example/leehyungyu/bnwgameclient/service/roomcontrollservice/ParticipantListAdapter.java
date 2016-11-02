package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.RoomItem;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.RoomItemData;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get.RoomState;
import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leehyungyu on 2016-11-02.
 */

public class ParticipantListAdapter extends BaseAdapter {

    private GuiContext gtx;
    private ArrayList<ParticipantListItemData> items = new ArrayList<>();

    public ParticipantListAdapter(GuiContext gtx, JSONArray data) {
        this.gtx = gtx;
        try
        {
            for(int i=0; i<data.length(); i++)
            {
                JSONObject row = (JSONObject)data.get(i);
                ParticipantListItemData itemData = new ParticipantListItemData();
                itemData.setNickname(row.getString("nickname"));
                itemData.setWin(row.getInt("win"));
                itemData.setDraw(row.getInt("draw"));
                itemData.setLose(row.getInt("lose"));
                itemData.setWinRate(row.getDouble("rate"));
                itemData.setReady(row.getBoolean("ready"));
                items.add(itemData);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParticipantItem item;

        if(convertView==null)
        {
            item = new ParticipantItem();

            LayoutInflater infl = (LayoutInflater) gtx.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.participant_list_item, parent, false);

            TextView nickname = (TextView)convertView.findViewById(R.id.nickname);
            TextView win = (TextView)convertView.findViewById(R.id.win);
            TextView draw = (TextView)convertView.findViewById(R.id.draw);
            TextView lose = (TextView)convertView.findViewById(R.id.lose);
            TextView rate = (TextView)convertView.findViewById(R.id.rate);
            TextView ready = (TextView)convertView.findViewById(R.id.ready);

            item.setNickname(nickname);
            item.setWin(win);
            item.setDraw(draw);
            item.setLose(lose);
            item.setRate(rate);
            item.setReadyState(ready);

            convertView.setTag(item);
        }
        else
        {
            item = (ParticipantItem)convertView.getTag();
        }

        ParticipantListItemData itemData = items.get(position);

        item.getNickname().setText(itemData.getNickname());
        item.getWin().setText(itemData.getWin()+"승");
        item.getDraw().setText(itemData.getDraw()+"무");
        item.getLose().setText(itemData.getLose()+"패");
        item.getRate().setText((Math.round(itemData.getWinRate()*100d)*100d)+"%");

        if(itemData.isReady())
        {
            item.getReadyState().setText("준비완료");
        }
        else
        {
            item.getReadyState().setText("-");
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(ParticipantListItemData data) {
        items.add(data);
    }

    public void removeItem(int position) {
        items.remove(position);
    }
}
