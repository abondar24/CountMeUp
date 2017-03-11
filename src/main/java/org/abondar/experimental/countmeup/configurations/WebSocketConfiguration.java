package org.abondar.experimental.countmeup.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


/**
 * Created by abondar on 3/10/17.
 */
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("org.abondar.experimental.countmeup")
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/ws-votes").setAllowedOrigins("*")
                .withSockJS();
    }


//    @Bean
//    SimpleAsyncTaskExecutor taskExecutor(){
//        return new SimpleAsyncTaskExecutor();
//    }
}
