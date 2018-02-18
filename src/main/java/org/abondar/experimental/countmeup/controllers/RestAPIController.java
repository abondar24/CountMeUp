package org.abondar.experimental.countmeup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper om = new ObjectMapper();

    public RestAPIController() {
        logger.info("REST Service started");
    }


    @RequestMapping(value = "/authorize_user", method = RequestMethod.POST)
    @ResponseBody
    public String authUser(@RequestParam(value = "token") String token) throws Exception {
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


        return om.writeValueAsString(user.getUserId());
    }


    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public String vote(@RequestParam(value = "candidate") String name,
                       @RequestParam(value = "competition") Integer competitionId,
                       @RequestParam(value = "user_id") String userId) throws Exception {


        Long compID = Long.valueOf(competitionId);
        Candidate candidate = mapper.findCandidateByName(name);
        Competition competition = mapper.findCompetitionById(compID);
        User user = mapper.findUserByUserId(userId);
        if (candidate != null && competition != null && user != null) {
            Integer voteAttempts = user.getVoteAttempts();
            if (voteAttempts <= 3) {
                Vote vote = new Vote(candidate.getId(), compID);
                mapper.insertOrUpdateVote(vote);
                voteAttempts += 1;
                user.setVoteAttempts(voteAttempts);
                mapper.insertOrUpdateUser(user);
                logger.info("Vote accepted: " + vote.toString());
                return om.writeValueAsString("vote accepted");
            } else {
                return om.writeValueAsString("use has used all of his attempts");
            }

        } else {
            return om.writeValueAsString("one or more wrong parameters");
        }


    }


    @RequestMapping(value = "/get_total_votes", method = RequestMethod.GET)
    @ResponseBody
    public String getTotalVotes(@RequestParam(value = "competition") Integer competitionId) throws Exception {

        Long compID = Long.valueOf(competitionId);
        Competition competition = mapper.findCompetitionById(compID);

        if (competition != null) {
            List<Vote> votes = mapper.findVotesForCompetition(compID);
            Integer votesCount = votes.size();
            return om.writeValueAsString(Long.valueOf(votesCount));

        } else {
            return om.writeValueAsString(0l);
        }


    }


    @RequestMapping(value = "/get_votes_for_candidate", method = RequestMethod.GET)
    @ResponseBody
    public String getTotalVotesOfCandidate(@RequestParam(value = "candidate") String name,
                                           @RequestParam(value = "competition") Integer competitionId,
                                           @RequestParam(value = "mode") String mode) throws Exception {

        Double res = 0.0;

        Long compID = Long.valueOf(competitionId);
        List<Vote> votes = mapper.findVotesForCandidate(name, compID);
        Integer voteCount = votes.size();


        if (mode.equals("percent")) {
            List<Vote> totalVotes = mapper.findVotesForCompetition(compID);
            Integer totalVoteCount = totalVotes.size();
            res = Double.valueOf(voteCount) / totalVoteCount;
        } else if (mode.equals("count")) {
            res = Double.valueOf(voteCount);
        }

        return om.writeValueAsString(res);
    }

    @RequestMapping(value = "/get_candidates_for_competition", method = RequestMethod.GET)
    @ResponseBody
    public String getCandidates(@RequestParam(value = "competition") Integer competitionId) throws Exception {

        Long compID = Long.valueOf(competitionId);
        List<Candidate> candidates = mapper.findCandidatesByCompetitionId(compID);
        return om.writeValueAsString(candidates);
    }


    @RequestMapping(value = "/get_competition", method = RequestMethod.GET)
    @ResponseBody
    public String getCompetition(@RequestParam(value = "competition") Integer competitionId) throws Exception {

        Long compID = Long.valueOf(competitionId);
        Competition competition = mapper.findCompetitionById(compID);
        return om.writeValueAsString(competition);
    }


    @RequestMapping(value = "/get_active_competition", method = RequestMethod.GET)
    @ResponseBody
    public String getActiveCompetition() throws Exception {

        Competition competition = mapper.findActiveCompetition();
        return om.writeValueAsString(competition);
    }


    // these methods are not used by web client

    @RequestMapping(value = "/add_competition", method = RequestMethod.POST)
    @ResponseBody
    public Long addCompetition() {

        Date date = new Date();
        Competition competition = new Competition(date.toString());
        mapper.insertOrUpdateCompetition(competition);
        logger.info("Competition added");
        return competition.getId();
    }


    @RequestMapping(value = "/add_candidate", method = RequestMethod.POST)
    @ResponseBody
    public String addCandidate(@RequestParam(value = "name") String name,
                               @RequestParam(value = "competition") Integer competitionId) throws Exception {

        Long compID = Long.valueOf(competitionId);
        Competition competition = mapper.findCompetitionById(compID);

        if (competition != null) {
            Candidate candidate = new Candidate(name, compID);
            mapper.insertOrUpdateCandidate(candidate);
            logger.info("Candidate added");
            return om.writeValueAsString(candidate);
        } else {
            logger.info("No competition found");
            return om.writeValueAsString("");
        }

    }

    @RequestMapping(value = "/start_competition", method = RequestMethod.POST)
    @ResponseBody
    public String startCompetition(@RequestParam(value = "competition") Integer competitionId) throws Exception {
        Long compID = Long.valueOf(competitionId);
        Competition competition = mapper.findCompetitionById(compID);
        if (competition != null) {
            competition.setActive(true);
            mapper.insertOrUpdateCompetition(competition);

            logger.info("Competition started");
            return om.writeValueAsString("competition started");
        } else {
            logger.info("No competition found");
            return om.writeValueAsString("No competition found");
        }


    }

    @RequestMapping(value = "/end_competition", method = RequestMethod.PUT)
    @ResponseBody
    public String endCompetition(@RequestParam(value = "competition") Integer competitionId) throws Exception {

        Long compID = Long.valueOf(competitionId);
        Competition competition = mapper.findCompetitionById(compID);
        if (competition != null) {
            competition.setActive(false);
            Date date = new Date();
            competition.setEndDate(date.toString());
            mapper.insertOrUpdateCompetition(competition);
            logger.info("Competition started");
            return om.writeValueAsString("competition started");
        } else {
            logger.info("No competition found");
            return om.writeValueAsString("No competition found");
        }

    }


}
