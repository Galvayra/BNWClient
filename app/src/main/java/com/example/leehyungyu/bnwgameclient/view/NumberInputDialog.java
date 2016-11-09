package com.example.leehyungyu.bnwgameclient.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.leehyungyu.bnwgameclient.R;
import com.example.leehyungyu.bnwgameclient.service.roomcontrollservice.RoomControllClient;

/**
 * Created by leehyungyu on 2016-11-09.
 */

public class NumberInputDialog extends Dialog {

    NumberInputDialog me;
    Button finish, cancel, clear;
    RoomControllClient rcc;
    TextView currentText;

    public NumberInputDialog(Context context, String title, RoomControllClient rcc) {
        super(context, android.support.design.R.style.AlertDialog_AppCompat);
        setTitle(title);
        this.rcc = rcc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.number_input_dialog);
        me = this;
        finish = (Button)findViewById(R.id.finish);
        cancel = (Button)findViewById(R.id.cancel);
        clear = (Button)findViewById(R.id.clear);
        currentText = (TextView)findViewById(R.id.current_number_text);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcc.endMyTurn(Integer.parseInt(currentText.getText().toString()));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.cancel();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText.setText("");
            }
        });


        Button[] numbers = {
            (Button)findViewById(R.id.number_0),
            (Button)findViewById(R.id.number_1),
            (Button)findViewById(R.id.number_2),
            (Button)findViewById(R.id.number_3),
            (Button)findViewById(R.id.number_4),
            (Button)findViewById(R.id.number_5),
            (Button)findViewById(R.id.number_6),
            (Button)findViewById(R.id.number_7),
            (Button)findViewById(R.id.number_8),
            (Button)findViewById(R.id.number_9)
        };

        for(int i=0;i<numbers.length;i++)
        {
            final int number = i;
            numbers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cur = currentText.getText().toString();

                    if(Integer.parseInt(cur+number)<100)
                    {
                        currentText.append(number+"");
                    }
                }
            });
        }
    }



}
