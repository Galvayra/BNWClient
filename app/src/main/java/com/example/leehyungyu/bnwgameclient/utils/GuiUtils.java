package com.example.leehyungyu.bnwgameclient.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by leehyungyu on 2016-10-17.
 */

public class GuiUtils {

    private static Context context;

    public static void setContext(Context _context) {
        context = _context;
    }

    public static void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
