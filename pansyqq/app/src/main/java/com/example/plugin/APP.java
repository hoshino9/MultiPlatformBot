package com.example.plugin;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.plugin.event.GroupEventReceiver;
import com.example.plugin.event.QQMessageReceiver;

import java.io.ByteArrayOutputStream;

public class APP extends Application {
    public static final String ACTION_QQMESSAGE_RECEIVE="com.pansyqq.receive.qqmessage";
    public static final String ACTION_GROUPEVENT_RECEIVE="com.pansyqq.receive.groupevent";
    public static final String ACTION_LOADPLUGIN_RECEIVE="com.pansyqq.receive.loadplugin";
    public static QQMessageReceiver messageReceiver;
    public static GroupEventReceiver groupEventReceiver;
    public static PansyAPI pansy;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
