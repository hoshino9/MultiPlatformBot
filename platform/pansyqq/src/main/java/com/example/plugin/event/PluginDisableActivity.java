package com.example.plugin.event;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import static com.example.plugin.APP.groupEventReceiver;
import static com.example.plugin.APP.messageReceiver;
/**
 * 插件禁用时调用
 */
public class PluginDisableActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注销QQ消息广播
        if(messageReceiver!=null){
            getApplicationContext().unregisterReceiver(messageReceiver);
            messageReceiver=null;
        }
        //注销群事件广播
        if(groupEventReceiver!=null){
            getApplicationContext().unregisterReceiver(groupEventReceiver);
            groupEventReceiver=null;
        }
        finish();
    }
}
