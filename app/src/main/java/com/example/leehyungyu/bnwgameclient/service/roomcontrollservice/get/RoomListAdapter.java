package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import static com.example.leehyungyu.bnwgameclient.utils.JsonUtils.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by leehyungyu on 2016-10-23.
 */

public class RoomListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RoomItemData> items = new ArrayList<>();

    public RoomListAdapter(Context context, JSONArray roomJsonData) {
        this.context = context;

        for(int i=0;i<roomJsonData.length();i++)
        {
            JSONObject obj = get(roomJsonData, i);
            RoomItemData itemData = new RoomItemData();
            itemData.setRoomNumber((int)get(obj,"room_no"));
            itemData.setRoomTitle(get(obj,"room_title").toString());
            itemData.setRoomCreator(get(obj, "creator").toString());
            itemData.setRoomState(RoomState.valueOf((int)get(obj, "room_state")).toString());
            Object participant = get(obj, "participant");
            if(participant==null)
            {
                itemData.setNumOfPart("1 / 2");
            }
            else
            {
                itemData.setNumOfPart("2 / 2");
            }
            items.add(itemData);
            Log.e("ffff", obj.toString());
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

        RoomItem item;

        if(convertView==null)
        {
            item = new RoomItem();

            LayoutInflater infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.room_list_item, parent, false);

            TextView numOf = (TextView)convertView.findViewById(R.id.numOfParticipant);
            TextView roomNo = (TextView)convertView.findViewById(R.id.room_no);
            TextView roomCreator = (TextView)convertView.findViewById(R.id.room_creator);
            TextView roomState = (TextView)convertView.findViewById(R.id.room_state);
            TextView roomTitle = (TextView)convertView.findViewById(R.id.room_title);

            item.setNumberOfParticipant(numOf);
            item.setRoomCreator(roomCreator);
            item.setRoomNumber(roomNo);
            item.setRoomState(roomState);
            item.setRoomTitle(roomTitle);

            convertView.setTag(item);
        }
        else
        {
            item = (RoomItem)convertView.getTag();
        }

        RoomItemData itemData = items.get(position);

        item.getNumberOfParticipant().setText(itemData.getNumOfPart());
        item.getRoomNumber().setText(itemData.getRoomNumber()+"");
        item.getRoomCreator().setText(itemData.getRoomCreator());
        item.getRoomState().setText(itemData.getRoomState());
        item.getRoomTitle().setText(itemData.getRoomTitle());

        if(itemData.getRoomState().equals(RoomState.WAIT.toString()))
        {
//            convertView.setBackgroundColor(Color.GREEN);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
