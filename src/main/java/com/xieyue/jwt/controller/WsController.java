package com.xieyue.jwt.controller;

import com.xieyue.jwt.utils.Result;
import com.xieyue.jwt.webSocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @program: jwt
 * @description:
 * @author: xieyue
 * @create: 2020-06-30 18:30
 **/

@RestController
@RequestMapping("ws")
public class WsController {

    @Autowired
    WebSocketServer webSocketServer;

    @GetMapping("index")
    public Result index(){
        return Result.result("请求成功");
    }


    @RequestMapping("/push/{toUserId}")
    public Result pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        webSocketServer.sendInfo(message,toUserId);
        return Result.result("MSG SEND SUCCESS");
    }

    @GetMapping("/sendAll/{msg}")
    public Result sendAllUser(@PathVariable String msg) throws IOException {
        webSocketServer.sendMessage(msg);
        return Result.result("MSG SEND SUCCESS");
    }


}
