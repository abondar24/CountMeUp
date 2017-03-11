package org.abondar.experimental.countmeup.controllers;

import org.abondar.experimental.countmeup.app.CountApplication;
import org.abondar.experimental.countmeup.model.Competition;
import org.abondar.experimental.countmeup.model.User;
import org.abondar.experimental.countmeup.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


/**
 * Created by abondar on 3/10/17.
 */
@RestController
@RequestMapping("/count_me")
public class RestAPIController {

    static Logger logger = LoggerFactory.getLogger(CountApplication.class);

    public RestAPIController() {
        logger.info("REST Service started");
    }


    @RequestMapping(value = "/authorize_user", method = RequestMethod.POST)
    @ResponseBody
    public String authUser(@RequestParam(value = "token") String token) {

        UUID userId = UUID.randomUUID();
        User user = new User(userId.toString(), token);
        logger.info("User authorzied with id " + userId.toString());
        return "auth complete";
    }


    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public String vote(@RequestParam(value = "candidate") Long candidate,
                       @RequestParam(value = "competition") Long competition) {

        Vote vote = new Vote(candidate, competition);


        return "vote accepted";
    }


    @RequestMapping(value = "/get_total_votes", method = RequestMethod.GET)
    @ResponseBody
    public Long getTotalVotes(@RequestParam(value = "competition") Long competition) {


        return 100L;
    }


    @RequestMapping(value = "/get_votes_of_candidate", method = RequestMethod.GET)
    @ResponseBody
    public Double getTotalVotesOfCandidate(@RequestParam(value = "candidate") String candidate,
                                           @RequestParam(value = "competition") Long competition,
                                           @RequestParam(value = "mode") String mode) {


        return 50.0;
    }

    @RequestMapping(value = "/add_competition", method = RequestMethod.PUT)
    @ResponseBody
    public void AddCompetition() {

        Date date = new Date();
        Competition competition = new Competition(date.toString());

    }

    @RequestMapping(value = "/start_competition", method = RequestMethod.PUT)
    @ResponseBody
    public void StartCompetition() {


    }

    @RequestMapping(value = "/end_competition", method = RequestMethod.PUT)
    @ResponseBody
    public void EndCompetition() {


    }


}
