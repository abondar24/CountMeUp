package org.abondar.experimental.countmeup.websocket;

import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;


public class VoteHandler extends TextWebSocketHandler {

    static Logger logger = LoggerFactory.getLogger(VoteHandler.class);
    WebSocketSession session;

    @Autowired
    private Mapper mapper;

    private Long competitionId;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        VoteTimer.mapper = mapper;
        VoteTimer.session = session;

        VoteTimer.addCandidateVotes(1l);
        VoteTimer.startTimer();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String compId = message.getPayload();
        competitionId = Long.valueOf(compId);
        logger.info(message.getPayload());


    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        VoteTimer.stopTimer();
    }
}