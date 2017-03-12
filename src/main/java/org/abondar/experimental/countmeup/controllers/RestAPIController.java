package org.abondar.experimental.countmeup.controllers;

import org.abondar.experimental.countmeup.app.CountApplication;
import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Competition;
import org.abondar.experimental.countmeup.model.User;
import org.abondar.experimental.countmeup.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/count_me")
public class RestAPIController {

    @Autowired
    private Mapper mapper;

    static Logger logger = LoggerFactory.getLogger(CountApplication.class);

    public RestAPIController() {
        logger.info("REST Service started");
    }


    @RequestMapping(value = "/authorize_user", method = RequestMethod.POST)
    @ResponseBody
    public String authUser(@RequestParam(value = "token") String token) {

        User user = mapper.findUserByToken(token);
        if (user != null) {
            if (user.getToken().equals(token)) {
                logger.info("User authorized ");
            }

        } else {
            UUID userId = UUID.randomUUID();
            user = new User(userId.toString(), token);
            mapper.insertOrUpdateUser(user);
            logger.info("User created and authorized");

        }
        return "auth complete";
    }


    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public String vote(@RequestParam(value = "candidate") String name,
                       @RequestParam(value = "competition") Long competitionId,
                       @RequestParam(value = "userId") String userId) {


        Candidate candidate = mapper.findCandidateByName(name);
        Competition competition = mapper.findCompetitionById(competitionId);
        User user = mapper.findUserByUserId(userId);
        if (candidate != null && competition != null && user != null) {
            Integer voteAttempts = user.getVoteAttempts();
            if (voteAttempts <= 3) {
                Vote vote = new Vote(candidate.getId(), competitionId);
                mapper.insertOrUpdateVote(vote);
                voteAttempts += 1;
                user.setVoteAttempts(voteAttempts);
                mapper.insertOrUpdateUser(user);
                logger.info("Vote accepted: " + vote.toString());
                return "vote accepted";
            } else {
                return "use has used all of his attempts";
            }

        } else {
            return "one or more wrong parameters";
        }


    }


    @RequestMapping(value = "/get_total_votes", method = RequestMethod.GET)
    @ResponseBody
    public Long getTotalVotes(@RequestParam(value = "competition") Long competitionId) {

        List<Vote> votes = mapper.findVotesForCompetition(competitionId);
        Integer votesCount = votes.size();

        return Long.valueOf(votesCount);
    }


    @RequestMapping(value = "/get_votes_for_candidate", method = RequestMethod.GET)
    @ResponseBody
    public Double getTotalVotesOfCandidate(@RequestParam(value = "candidate") String name,
                                           @RequestParam(value = "competition") Long competitionId,
                                           @RequestParam(value = "mode") String mode) {

        Double res = 0.0;

        List<Vote> votes = mapper.findVotesForCandidate(name, competitionId);
        Integer voteCount = votes.size();


        if (mode.equals("percent")) {
            List<Vote> totalVotes = mapper.findVotesForCompetition(competitionId);
            Integer totalVoteCount = totalVotes.size();
            res = Double.valueOf(voteCount) / totalVoteCount;
        } else if (mode.equals("count")) {
            res = Double.valueOf(voteCount);
        }

        return res;
    }

    @RequestMapping(value = "/get_candidates_for_competition", method = RequestMethod.GET)
    @ResponseBody
    public List<Candidate> getCandidates(@RequestParam(value = "competition") Long competitionId) {

        return mapper.findCandidatesByCompetitionId(competitionId);
    }


    @RequestMapping(value = "/get_competition", method = RequestMethod.GET)
    @ResponseBody
    public Competition getCompetition(@RequestParam(value = "competition") Long competitionId) {

        return mapper.findCompetitionById(competitionId);
    }


    // these methods are not used by web client

    @RequestMapping(value = "/add_competition", method = RequestMethod.PUT)
    @ResponseBody
    public void addCompetition() {

        Date date = new Date();
        Competition competition = new Competition(date.toString());
        mapper.insertOrUpdateCompetition(competition);
        logger.info("Competition added");
    }




    @RequestMapping(value = "/add_candidate", method = RequestMethod.POST)
    @ResponseBody
    public Candidate addCandidate(@RequestParam(value = "name") String name,
                             @RequestParam(value = "competition") Long competitionId) {

        Competition competition = mapper.findCompetitionById(competitionId);

        if (competition!=null) {
            Candidate candidate = new Candidate(name, competitionId);
            mapper.insertOrUpdateCandidate(candidate);
            logger.info("Candidate added");
            return candidate;
        }  else{
            logger.info("No competition found");
            return null;
        }

    }

    @RequestMapping(value = "/start_competition", method = RequestMethod.POST)
    @ResponseBody
    public String startCompetition(@RequestParam(value = "competition") Long competitionId) {
        Competition competition = mapper.findCompetitionById(competitionId);
        if (competition!=null) {
            competition.setActive(true);
            mapper.insertOrUpdateCompetition(competition);

            logger.info("Competition started");
            return "competition started";
        }  else{
            logger.info("No competition found");
            return "No competition found";
        }


    }

    @RequestMapping(value = "/end_competition", method = RequestMethod.PUT)
    @ResponseBody
    public String endCompetition(@RequestParam(value = "competition") Long competitionId) {

        Competition competition = mapper.findCompetitionById(competitionId);
        if (competition!=null) {
            competition.setActive(false);
            Date date = new Date();
            competition.setEndDate(date.toString());
            mapper.insertOrUpdateCompetition(competition);
            logger.info("Competition started");
            return "competition started";
        }  else{
            logger.info("No competition found");
            return "No competition found";
        }

    }


}
