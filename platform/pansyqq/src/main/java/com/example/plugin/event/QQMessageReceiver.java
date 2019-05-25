package com.example.plugin.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.hoshino9.handle.Main;
import org.hoshino9.handle.Money;
import org.hoshino9.handle.SharpChess;
import org.hoshino9.robot.dialog.Group;
import org.hoshino9.robot.dialog.Member;
import org.hoshino9.robot.handle.HandlerContainer;
import org.hoshino9.robot.handle.MessageHandler;
import org.hoshino9.robot.handle.MessageReceiveHandler;
import org.hoshino9.robot.message.RawStringMessage;
import org.hoshino9.robot.parser.internal.InternalMessageParser;

import java.util.LinkedList;
import java.util.List;

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
            /*
             * 图片格式：
             * 网络图片:[img:url="xxx"],xxx为图片网址
             * 本地图片:[img:path="xxx"],xxx为图片在设备上的绝对路径
             * 普通表情：[face:xxx],xxx为表情数字
             * 艾特格式：[At:xxx],xxx为艾特的对方QQ
             */

            List<HandlerContainer.Factory> handlers = new LinkedList<>();
            handlers.add(Main.Companion);
            handlers.add(Money.Companion);
            handlers.add(SharpChess.Companion);

            MessageReceiveHandler.Context ctx = new MessageReceiveHandler.Context(
                    InternalMessageParser.INSTANCE,
                    handlers,
                    msg_type == 0 ? new Group(gn) : new Member(QQ),
                    new Member(QQ),
                    new RawStringMessage(msg)
            );

            if(msg_type==QM_GROUP) {
                MessageHandler.INSTANCE.handle(ctx);
            } else if(msg_type==QM_FRIEND){
                //pansy.sendFriendMessage(QQ,"你发送了一条好友消息");
            }
        }
    }

}
