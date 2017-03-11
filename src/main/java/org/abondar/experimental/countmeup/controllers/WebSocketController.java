package org.abondar.experimental.countmeup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abondar on 3/10/17.
 */
@Controller
public class WebSocketController {




//    @Autowired
//    @Qualifier("messageBrokerTaskScheduler")
//    private TaskScheduler taskScheduler;
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
    private List<String> messages = new ArrayList<>();


    @MessageMapping("/addVotes")
    @SendTo("/topic/votes")
    @CrossOrigin
    public List<String> addVotes() throws Exception{
        for (int i=0;i<100;i++) {
            messages.add("ss " + i);
        }

        return messages;
    }
//
//    public void broadcastVotes(){
//        for (int i=0;i<100;i++) {
//            messages.add("ss " + i);
//        }
//
//        simpMessagingTemplate.convertAndSend("/topic/votes",messages);
//    }
//
//    @PostConstruct
//    private void broacastVotesPeriodically(){
//        taskScheduler.scheduleAtFixedRate(this::broadcastVotes,500);
//    }

}
