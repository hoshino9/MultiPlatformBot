package com.example.plugin.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static com.example.plugin.APP.ACTION_QQMESSAGE_RECEIVE;
import static com.example.plugin.APP.pansy;

//收到QQ消息时响应
public class QQMessageReceiver extends BroadcastReceiver {
    private final static int QM_GROUP=0;//群消息
    private final static int QM_FRIEND=1;//好友消息
    private final static int QM_GRUOP_ENVELOPE=2;//群红包
    private final static int QM_FRIEND_ENVELOPE=3;//好友红包(未实现)
    private final static int QM_FRIEND_TRANSFER=4;//好友转账
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_QQMESSAGE_RECEIVE)){
            int msg_type=intent.getIntExtra("msg_type",-1);//消息类型
            long gn=intent.getLongExtra("gn",-1);//群号，msg_type为1时值为-1
            long QQ=intent.getLongExtra("QQ",-1);//QQ，msg_type为0时值为发出消息的群成员，msg_type为1时值为好友QQ
            String msg=intent.getStringExtra("msg");//消息内容
            long withdraw_seq=intent.getLongExtra("withdraw_seq",-1);//撤回消息参数
            long withdraw_id=intent.getLongExtra("withdraw_id",-1);//撤回消息参数
            /*
             * 图片格式：
             * 网络图片:[img:url="xxx"],xxx为图片网址
             * 本地图片:[img:path="xxx"],xxx为图片在设备上的绝对路径
             * 普通表情：[face:xxx],xxx为表情数字
             * 艾特格式：[At:xxx],xxx为艾特的对方QQ
             */
            if(msg_type==QM_GROUP){
                //pansy.sendGroupMessage(gn,"[At:"+QQ+"]你发送了一条群消息");
                if(msg.equals("傻逼")){
                    //撤回该条群消息
                    //pansy.withdraw(gn,withdraw_seq,withdraw_id);
                }
            }else if(msg_type==QM_FRIEND){
                //pansy.sendFriendMessage(QQ,"你发送了一条好友消息");
            }else if(msg_type==QM_GRUOP_ENVELOPE){
                byte[] envelope_p1=intent.getByteArrayExtra("envelope_p1");//抢红包参数
                byte[] envelope_p2=intent.getByteArrayExtra("envelope_p2");//抢红包参数
                byte[] envelope_p3=intent.getByteArrayExtra("envelope_p3");//抢红包参数
                String envelope_remark=intent.getStringExtra("envelope_remark");//红包备注或红包口令
                //double money=pansy.robEnvelope(gn,envelope_p1,envelope_p2,envelope_p3);
                //if(money>-1)
                    //pansy.sendGroupMessage(gn,"我领到了"+money+"元红包,备注:"+envelope_remark);
            }else if(msg_type==QM_FRIEND_TRANSFER){
                double money=intent.getDoubleExtra("money",-1);
                String remark=intent.getStringExtra("remark");
                //pansy.sendFriendMessage(QQ,"我已收到你的"+money+"元转账,备注:"+remark);
            }
        }
    }

}
