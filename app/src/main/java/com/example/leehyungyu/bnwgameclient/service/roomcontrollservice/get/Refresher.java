package com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.get;

import android.util.Log;

import com.example.leehyungyu.bnwgameclient.view.gui.GuiContext;

/**
 * Created by leehyungyu on 2016-10-25.
 */

public class Refresher extends Thread {

    private GuiContext context;
    private long gap = 2000;

    public Refresher(GuiContext context, long gap) {
        this.context = context;
        this.gap = gap;
    }

    public void setGap(long gap) {
        this.gap = gap;
    }

    @Override
    public void run() {
        while(true)
        {
            synchronized (this)
            {
                try
                {
                    this.wait(gap);
                    Log.e("service","방 목록 새로고침");
                    new GetRoomListService(context).runOnBackground();
                }
                catch(InterruptedException e)
                {
                    Log.e("service","새로고침 서비스 중단");
                    return;
                }
            }
        }
    }
}
