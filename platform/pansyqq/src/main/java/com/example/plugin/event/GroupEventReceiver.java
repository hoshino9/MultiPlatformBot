package com.example.plugin.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static com.example.plugin.APP.ACTION_GROUPEVENT_RECEIVE;
import static com.example.plugin.APP.pansy;

//群事件
public class GroupEventReceiver extends BroadcastReceiver {
    private final static int GE_APPLY_JOIN=0;//有人申请进群
    private final static int GE_JOIN=1;//有人进群
    private final static int GE_QUIT=2;//有人退群
    private final static int GE_KICK=3;//有人被踢
    private final static int GE_BECOME_GM=4;//有人被升为管理
    private final static int GE_CANCEL_GM=5;//有人被取消管理
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_GROUPEVENT_RECEIVE)){
            int event_type=intent.getIntExtra("event_type",-1);//事件类型
            long gn=intent.getLongExtra("gn",-1);//群号
            long QQ=intent.getLongExtra("QQ",-1);//QQ
            long operator=intent.getLongExtra("operator",-1);//操作人/邀请人QQ

            if(event_type==GE_APPLY_JOIN){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"申请进群");
            }else if(event_type==GE_JOIN){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"进群了");
            }else if(event_type==GE_QUIT){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"退群了");
            }else if(event_type==GE_KICK){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"被踢了");
            }else if(event_type==GE_BECOME_GM){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"被升为管理");
            }else if(event_type==GE_CANCEL_GM){
                //pansy.sendGroupMessage(gn,"QQ:"+QQ+"被取消管理");
            }
        }
    }
}
