package org.abondar.experimental.countmeup.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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


    public static void tick() throws Exception {

        List<CandidateVote> candidateVotes = new ArrayList<>();

        Long competitionId = mapper.findActiveCompetitionId();
        if (competitionId==0){
            session.sendMessage(new TextMessage("No active competitions now"));
        }
        List<Vote> votes = mapper.findVotesForCompetition(competitionId);
        if (!votes.isEmpty()) {
           for (Vote vote : votes) {
               Candidate candidate = mapper.findCandidateById(vote.getCandidateId());
               List<Vote> candVotes = mapper.findVotesForCandidate(candidate.getName(), competitionId);
               candidateVotes.add(new CandidateVote(candidate.getName(), candVotes.size()));
               logger.info(candidate.getName() + " " + candVotes.size());
           }
        } else {
           List<Candidate> candidates = mapper.findCandidatesByCompetitionId(competitionId);
           for (Candidate candidate: candidates){
               candidateVotes.add(new CandidateVote(candidate.getName(),0));
               logger.info(candidate.getName() + " " + 0);
           }
       }

        session.sendMessage(new TextMessage(formMessage(candidateVotes)));
    }


    public static String formMessage(List<CandidateVote> candidateVotes) throws Exception{
        ObjectMapper om = new ObjectMapper();
        ObjectWriter writer = om.writer();
        return writer.writeValueAsString(candidateVotes);
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
