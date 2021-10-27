package com.zhbr.gs_sparklauncher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
@ServerEndpoint("/websocket/{socketname}")
public class WebSocketServer {

    /**
     * 以通道名称为key，连接会话为对象保存起来
     */
    public static Map<String, Session> websocketClients = new ConcurrentHashMap<String, Session>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 通道名称
     */
    private String socketname;

    /**
     * 发送消息到指定连接
     * @param socketname 连接名
     * @param jsonString 消息
     */
    public void sendMessage(String socketname,String jsonString){
        Session nowsession = websocketClients.get(socketname);
        if(nowsession!=null){
            try {
                nowsession.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            log.info("session为空");
        }
    }

    @OnOpen
    public void onOpen(@PathParam("socketname") String socketname, Session session)
    {
        this.socketname = socketname;
        this.session = session;
        if(websocketClients.get(socketname)==null){
            websocketClients.put(socketname, session);
            log.info("当前socket通道"+socketname+"已加入连接");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端发生了错误"+error.getMessage());
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(@PathParam("socketname") String socketname)
    {
        websocketClients.remove(socketname);
        log.info("当前socket通道"+socketname+"已退出连接");
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(@PathParam("socketname") String socketname,String message, Session session){
        log.info("当前收到了消息："+message);
        //session.getAsyncRemote().sendText("来自服务器："+this.socketname+"你的消息我收到啦");
        sendMessage(socketname,message);
    };
}
