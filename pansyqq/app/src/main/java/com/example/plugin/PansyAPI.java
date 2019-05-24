package com.example.plugin;

import com.qihoo360.replugin.RePlugin;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

public class PansyAPI {
    private static ClassLoader loader;
    private static String packageName;
    private static ExecutorService threadPool=Executors.newCachedThreadPool();
    final static String CLASS="com.pansy.robot.protocol.QQAPI";
    public PansyAPI(String packageName){
        this.packageName=packageName;
       loader=RePlugin.getHostClassLoader();
    }

    /**
     * 发送群消息
     * @param gn
     * @param msg
     */
    public void sendGroupMessage(long gn,String msg){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendGroupMessage", String.class, long.class, String.class);
                m.invoke(null,packageName,gn,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送群消息，xml
     * @param gn
     * @param xml
     */
    public void sendGroupXml(long gn,String xml){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendGroupXml", String.class, long.class, String.class);
                m.invoke(null,packageName,gn,xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送群消息，json
     * @param gn
     * @param json
     */
    public void sendGroupJson(long gn,String json){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendGroupJson", String.class, long.class, String.class);
                m.invoke(null,packageName,gn,json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送群语音
     * @param gn
     * @param voice silk编码语音
     * @param seconds 语音显示的时长，单位：秒
     */
    public void sendGroupVoice(long gn,byte[] voice,int seconds){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendGroupVoice", String.class, long.class, byte[].class,int.class);
                m.invoke(null,packageName,gn,voice,seconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送好友消息
     * @param QQ
     * @param msg
     */
    public void sendFriendMessage(long QQ,String msg){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendFriendMessage", String.class, long.class, String.class);
                m.invoke(null,packageName,QQ,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送好友消息，xml
     * @param QQ
     * @param xml
     */
    public void sendFriendXml(long QQ,String xml){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendFriendXml", String.class, long.class, String.class);
                m.invoke(null,packageName,QQ,xml);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送好友消息，json
     * @param QQ
     * @param json
     */
    public void sendFriendJson(long QQ,String json){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("sendFriendJson", String.class, long.class, String.class);
                m.invoke(null,packageName,QQ,json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 撤回一条群消息
     * @param gn
     * @param withdraw_seq
     * @param withdraw_id
     */
    public void withdraw(long gn,long withdraw_seq,long withdraw_id){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("withdraw", String.class,long.class, long.class, long.class);
                m.invoke(null,packageName,gn,withdraw_seq,withdraw_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 抢红包
     * @param gn
     * @param envelope_p1
     * @param envelope_p2
     * @param envelope_p3
     * @return 返回红包金额
     */
    public double robEnvelope(final long gn,final byte[] envelope_p1,final byte[] envelope_p2,final byte[] envelope_p3){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<Double> future=threadPool.submit(new Callable<Double>() {
                    @Override
                    public Double call() throws Exception {
                        Method m = clz.getDeclaredMethod("robEnvelope", String.class,long.class,byte[].class,byte[].class,byte[].class);
                        return (Double) m.invoke(null,packageName,gn,envelope_p1,envelope_p2,envelope_p3);
                    }
                });
                return future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 禁言
     * @param gn
     * @param QQ
     * @param seconds 禁言时间，单位：秒，值为0时解禁
     */
    public String shutup(final long gn,final long QQ,final long seconds){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("shutup",String.class, long.class, long.class, long.class);
                        return (String)m.invoke(null,packageName,gn,QQ,seconds);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 全体禁言
     * @param gn
     * @param isShutup true禁言，false解除禁言
     * @return
     */
    public String shutupAll(final long gn,final boolean isShutup){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("shutupAll",String.class, long.class, boolean.class);
                        return (String)m.invoke(null,packageName,gn,isShutup);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 踢人
     * @param gn
     * @param QQ
     */
    public String kick(final long gn,final long QQ){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("kick",String.class, long.class, long.class);
                        return (String)m.invoke(null,packageName,gn,QQ);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取QQ昵称
     * @param QQ
     */
    public String getNick(final long QQ){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getNick",String.class, long.class);
                        return (String)m.invoke(null,packageName,QQ);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取群信息
     * @param gn
     * @return
     */
    public String getGroupInfo(final long gn){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getGroupInfo",String.class, long.class);
                        return (String)m.invoke(null,packageName,gn);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取群名
     * @param gn
     * @return
     */
    public String getGroupName(final long gn){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getGroupName",String.class, long.class);
                        return (String)m.invoke(null,packageName,gn);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取群成员名片
     * @param gn
     * @param QQ
     */
    public String getGroupCard(final long gn,final long QQ){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getGroupCard",String.class, long.class, long.class);
                        return (String)m.invoke(null,packageName,gn,QQ);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 设置群成员名片
     * @param gn
     * @param QQ
     */
    public String setGroupCard(final long gn,final long QQ,final String card){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("setGroupCard",String.class, long.class, long.class,String.class);
                        return (String)m.invoke(null,packageName,gn,QQ,card);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取好友列表
     */
    public String getFriendList(){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getFriendList",String.class);
                        return (String)m.invoke(null,packageName);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取群列表
     */
    public String getGroupList(){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("getGroupList",String.class);
                        return (String)m.invoke(null,packageName);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 进群处理
     * @param gn
     * @param QQ
     * @param state 1同意，2拒绝，3忽略
     * @return
     */
    public String joinGroupDispose(final long gn,final long QQ,final int state){
        try {
            final Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Future<String> future=threadPool.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Method m = clz.getDeclaredMethod("joinGroupDispose",String.class, long.class, long.class,int.class);
                        return (String)m.invoke(null,packageName,gn,QQ,state);
                    }
                });
                return future.get();
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取登录QQ
     * @return
     */
    public long getQQ(){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("getQQ", String.class);
                return (long)m.invoke(null,packageName);
            }else{
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取登录QQ的cookies
     * @return
     */
    public String getCookies(){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("getCookies", String.class);
                return (String)m.invoke(null,packageName);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取登录QQ的gtk,也称bkn
     * @return
     */
    public String getGtk(){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("getGtk", String.class);
                return (String)m.invoke(null,packageName);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 点赞，非好友点赞有限制
     */
    public void praise(long QQ){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("praise", String.class,long.class);
                m.invoke(null,packageName,QQ);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印日志
     */
    public void log(String name,String msg){
        try {
            Class clz=loader.loadClass(CLASS);
            if(clz!=null && packageName!=null) {
                Method m = clz.getDeclaredMethod("log", String.class,String.class,String.class);
                m.invoke(null,packageName,name,msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
