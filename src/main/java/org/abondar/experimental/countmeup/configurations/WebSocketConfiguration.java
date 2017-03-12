package org.abondar.experimental.countmeup.configurations;

import org.abondar.experimental.countmeup.websocket.VoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.*;



@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
         registry.addHandler(votesHanlder(),"/echo").withSockJS();
    }


    @Bean
    public VoteHandler votesHanlder(){
        return new VoteHandler();
    }

}
