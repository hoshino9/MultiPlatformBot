package com.example.plugin.event;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import com.example.plugin.PansyAPI;
import com.example.plugin.R;
import org.hoshino9.api.Api;

import static com.example.plugin.APP.ACTION_GROUPEVENT_RECEIVE;
import static com.example.plugin.APP.ACTION_QQMESSAGE_RECEIVE;
import static com.example.plugin.APP.groupEventReceiver;
import static com.example.plugin.APP.messageReceiver;
import static com.example.plugin.APP.pansy;

/**
 * 插件启用时调用
 */
public class PluginEnableActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册QQ消息广播
        if(messageReceiver==null){
            messageReceiver=new QQMessageReceiver();
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(ACTION_QQMESSAGE_RECEIVE);
            getApplicationContext().registerReceiver(messageReceiver,intentFilter);
        }
        //注册群事件广播
        if(groupEventReceiver==null){
            groupEventReceiver=new GroupEventReceiver();
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction(ACTION_GROUPEVENT_RECEIVE);
            getApplicationContext().registerReceiver(groupEventReceiver,intentFilter);
        }
        //实例化API类
        if(pansy==null){
            pansy=new PansyAPI("com.example.plugin");
        }

        Api.INSTANCE.setRoot(Environment.getExternalStorageDirectory().toPath().resolve("hoshino-plugin").toString());

        finish();
    }
}
