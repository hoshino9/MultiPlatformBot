package com.example.plugin.event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import com.example.plugin.R;
import com.qihoo360.replugin.loader.a.PluginActivity;
import java.io.ByteArrayOutputStream;
import static com.example.plugin.APP.ACTION_LOADPLUGIN_RECEIVE;

/**
 * 插件初始化时调用
 */
public class PluginInitActivity extends PluginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent();
        intent.setAction(ACTION_LOADPLUGIN_RECEIVE);
        intent.putExtra("plugin_packageName","com.example.plugin");//插件包名
        intent.putExtra("plugin_name","Hoshino Plugin");//插件名称
        intent.putExtra("plugin_brief","This is a demo plugin");//插件简介
        intent.putExtra("plugin_author","Hoshino");//插件作者
        intent.putExtra("plugin_version","1.0.0");//插件版本
        intent.putExtra("plugin_icon",res2ByteArray(R.drawable.plugin_icon));//插件图标
        sendBroadcast(intent);
        finish();
    }
    private byte[] res2ByteArray(int id){
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),id);
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        return baos.toByteArray();
    }
}
