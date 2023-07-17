package com.websocket.demo.controller;

import com.websocket.demo.handler.SocketTextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/demo")
public class DemoWebsocketController {

    @Autowired
    SocketTextHandler socketTextHandler;

    @GetMapping(value = "/upload")
    public String uploadDoc() {
        return "{ \"status\": \"message sent\" }";
    }

    @GetMapping(value = "/sendmsg/{userId}/{message}")
    public String sendMessage(@PathVariable(value = "userId") final String userId,
                              @PathVariable(value = "message") final String message) {
        socketTextHandler.sendMessageToClient(userId, message);
        return "{ \"status\": \"message sent\" }";
    }
}
