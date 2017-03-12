package org.abondar.experimental.countmeup.websocket;

import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;


/**
 * Created by abondar on 3/12/17.
 */
public class VoteTimer {

    private static Logger logger = LoggerFactory.getLogger(VoteTimer.class);

    private static Timer voteTimer = null;

    private static final long TICK_DELAY = 500;

    public static Mapper mapper;

    public static WebSocketSession session;

    private  static Vector<CandidateVote> candidateVotes = new Vector<>();


    public static synchronized void addCandidateVotes(Long competitionId) {
        List<Vote> votes = mapper.findVotesForCompetition(competitionId);
        logger.info("ss1");
        for (Vote vote : votes) {
            Candidate candidate = mapper.findCandidateById(vote.getCandidateId());
            List<Vote> candVotes = mapper.findVotesForCandidate(candidate.getName(), competitionId);
            candidateVotes.add(new CandidateVote(candidate.getName(),candVotes.size()));
            logger.info(candidate.getName() + " "+candVotes.size());
        }


    }



    public static void tick() throws Exception {
        logger.info("ss21");

        for (int i=0; i<10;i++){
            session.sendMessage(new TextMessage("ss21"));
        }
//        for (CandidateVote candidateVote : candidateVotes) {
//           session.sendMessage(new TextMessage(candidateVote.toString()));
//        }

    }



    public static void startTimer() {
        voteTimer = new Timer(VoteTimer.class.getSimpleName() + " Timer");
        voteTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    tick();
                }
                catch (Throwable ex) {
                    logger.error("Caught to prevent timer from shutting down", ex);
                }
            }
        }, TICK_DELAY, TICK_DELAY);
    }

    public static void stopTimer() {
        if (voteTimer != null) {
            voteTimer.cancel();
        }
    }
}
