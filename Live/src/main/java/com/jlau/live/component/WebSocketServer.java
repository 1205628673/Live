package com.jlau.live.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlau.live.Entity.Bullet;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.ErrorManager;

/**
 * Created by cxr1205628673 on 2019/6/28.
 */
@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {
    static Log log = LogFactory.getLog(WebSocketServer.class);
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private Session session;//与某个客户端的通话，需要使用sessi   on来与客户发送数据
    private String sid;//接受的sid
    @OnOpen
    public void onOpen(Session session, @PathParam("sid")String sid){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("有新的窗口监听:"+sid+" , 当前在线 "+onlineCount);
        ObjectMapper objectMapper = new ObjectMapper();
        Bullet bullet = new Bullet();
        try {
            bullet.setOnlineCount(getOnlineCount());
            bullet.setMsg("");
            bullet.setColor("pink");
            String msg = objectMapper.writeValueAsString(bullet);
            sendInfo(msg,null);
        }catch (IOException e){
            log.error(e.getCause());
        }
        /*
        this.sid = sid;
        try{
            sendMessage("连接成功");
        }catch (IOException e){
            e.printStackTrace();
            log.error(e.getCause());
        }*/
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);//从set中删除
        subOnlineCount();
        log.info("一个连接关闭");
    }
    @OnMessage
    public void onMessage(String msg,Session session){
        log.info("从一个客户端收到消息");
        System.out.println(msg);
        for(WebSocketServer ws:webSocketSet){
            try {
                ws.sendMessage(msg);//向set中所有的客户主动发送消息
            }catch (IOException e){
                log.error(e.getCause());
            }
        }
    }
    @OnError
    public void onError(Session session, Throwable error){
        log.error("错误："+error.getCause());
        error.printStackTrace();
    }
    public  void sendInfo(String msg,@PathParam("sid")String sid) throws IOException{
        log.info("推送消息到sid: "+sid+" 推送内容: "+msg.toString());
        for(WebSocketServer ws : webSocketSet){
            if(sid == null){//如果为空则向所有客户推送消息
                ws.sendMessage(msg);
            }else if(sid.equals(ws.sid)){
                ws.sendMessage(msg);
            }
        }
    }
    public void sendMessage(String msg) throws IOException{
        //session.getBasicRemote().sendText(message);//实现向远程客户主动发送消息,服务端做了"客户端"
        try {
            session.getBasicRemote().sendText(msg);
        }catch (Exception e){
            log.error(e.getCause());
        }
    }
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        onlineCount--;
    }
}
