package org.abondar.experimental.countmeup.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by abondar on 3/11/17.
 */
public class VoteHandler extends TextWebSocketHandler {

    static Logger logger = LoggerFactory.getLogger(VoteHandler.class);
    WebSocketSession session;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Connection established");
        this.session = session;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage(message.getPayload()));
        logger.info(message.getPayload());
    }
}
