package com.example.plugin.event;

import android.os.Bundle;
import com.example.plugin.R;
import com.qihoo360.replugin.loader.a.PluginActivity;

/**
 * 用户点击设置时调用
 */
public class PluginSetActivity extends PluginActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
