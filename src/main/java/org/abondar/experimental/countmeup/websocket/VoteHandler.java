package org.abondar.experimental.countmeup.websocket;

import org.abondar.experimental.countmeup.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



public class VoteHandler extends TextWebSocketHandler {

    @Autowired
    private Mapper mapper;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        VoteTimer.mapper = mapper;
        VoteTimer.session = session;

        VoteTimer.startTimer();
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        VoteTimer.stopTimer();
    }
}