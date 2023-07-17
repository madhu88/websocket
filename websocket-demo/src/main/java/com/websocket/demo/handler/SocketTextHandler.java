package com.websocket.demo.handler;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketTextHandler extends TextWebSocketHandler {


    static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage)
            throws InterruptedException, IOException {
        String payload = textMessage.getPayload();
        JSONObject jsonObject = new JSONObject(payload);
        System.out.println("getId() = " + webSocketSession.getId());
        if (null != jsonObject.get("user") && null == sessions.get(jsonObject.get("user").toString())) {
            sessions.put(jsonObject.get("user").toString(), webSocketSession);
        }
        System.out.println("Session map = " + sessions);
        webSocketSession.sendMessage(new TextMessage("Hi " + jsonObject.get("user")
                + " how may we help you?"));

    }

    public void sendMessageToClient(final String userId, final String message) {
        System.out.println("Session map = " + sessions);
        if (null != sessions.get(userId)) {
            try {
                sessions.get(userId).sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Session not found for user id [ " + userId + " ]");
        }
    }

}
