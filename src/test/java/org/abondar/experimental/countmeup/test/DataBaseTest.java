package org.abondar.experimental.countmeup.test;


import org.abondar.experimental.countmeup.app.CountApplication;
import org.abondar.experimental.countmeup.configurations.DataBaseConfiguration;
import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Competition;
import org.abondar.experimental.countmeup.model.User;
import org.abondar.experimental.countmeup.model.Vote;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(DataBaseConfiguration.class)
@SpringBootApplication(scanBasePackageClasses = CountApplication.class)
public class DataBaseTest {
    static Logger logger = LoggerFactory.getLogger(DataBaseTest.class);

    @Autowired
    private Mapper mapper;

    @Test
    public void testInsertUser() {
        logger.info("Insert User Test");
        mapper.deleteAllUsers();

        UUID uuid = UUID.randomUUID();
        String userID = uuid.toString();
        User user = new User(uuid.toString(), "sadadsad");

        mapper.insertOrUpdateUser(user);

        User foundUser = mapper.findUserByUserId(userID);
        logger.info(foundUser.toString());
        assertTrue("User Id is fine", foundUser.getUserId().equals(userID));

        mapper.deleteAllUsers();
    }


    @Test
    public void testFindUserByToken() {
        logger.info("Find User By Token Test");
        mapper.deleteAllUsers();

        UUID uuid = UUID.randomUUID();
        String userID = uuid.toString();
        String token = "sadadsad";
        User user = new User(uuid.toString(), token);

        mapper.insertOrUpdateUser(user);

        User foundUser = mapper.findUserByToken(token);
        logger.info(foundUser.toString());
        assertTrue("User found by token", foundUser.getToken().equals(token));

        mapper.deleteAllUsers();
    }


    @Test
    public void testCompetition() {
        logger.info("Insert Competition Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());

        mapper.insertOrUpdateCompetition(competition);

        List<Competition> competitions = mapper.findAllCompetitions();
        Competition foundCompetition = competitions.get(0);
        logger.info(foundCompetition.toString());
        assertTrue("Competition inserted ",
                foundCompetition.getStartDate().equals(startDate.toString()));


    }

    @Test
    public void testFindCompetitionById() {
        logger.info("Find Competition by id Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());

        mapper.insertOrUpdateCompetition(competition);

        List<Competition> competitions = mapper.findAllCompetitions();
        Competition foundCompetition = competitions.get(0);
        logger.info(foundCompetition.toString());

        Competition foundById = mapper.findCompetitionById(foundCompetition.getId());
        logger.info(foundById.toString());
        assertTrue("Competition found by id successfully",
                foundCompetition.getId().equals(foundById.getId()));
    }

    @Test
    public void testUpdateCompetitionDate() {
        logger.info("Find Competition by id Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());

        mapper.insertOrUpdateCompetition(competition);

        List<Competition> competitions = mapper.findAllCompetitions();
        Competition foundCompetition = competitions.get(0);
        logger.info(foundCompetition.toString());

        Date endDate = new Date();
        foundCompetition.setEndDate(endDate.toString());
        mapper.insertOrUpdateCompetition(foundCompetition);
        Competition updatedDate = mapper.findCompetitionById(foundCompetition.getId());
        logger.info(updatedDate.toString());
        assertTrue("Competition's end date updated",
                updatedDate.getEndDate().equals(endDate.toString()));

        foundCompetition.setActive(true);
        mapper.insertOrUpdateCompetition(foundCompetition);
        Competition activeComp = mapper.findCompetitionById(foundCompetition.getId());
        logger.info(activeComp.toString());
        assertTrue("Competition activated", activeComp.getActive());

    }

    @Test
    public void testFindActiveCompetition() {
        logger.info("Find Active Competition ");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());
        competition.setActive(true);
        mapper.insertOrUpdateCompetition(competition);

        List<Competition> competitions = mapper.findAllCompetitions();
        Competition foundCompetition = competitions.get(0);
        logger.info(foundCompetition.toString());

        Long activeCompetitionId =mapper.findActiveCompetitionId();

        assertTrue("Competition found", foundCompetition.getId().equals(activeCompetitionId));

    }


    @Test
    public void testCandidate() {
        logger.info("Insert Candidate Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());
        mapper.insertOrUpdateCompetition(competition);
        List<Competition> competitions = mapper.findAllCompetitions();
        competition = competitions.get(0);

        String name = "Vasya Pupkin";
        Candidate candidate = new Candidate(name,competition.getId());

        mapper.insertOrUpdateCandidate(candidate);
        Candidate foundCandidate = mapper.findCandidateByName(name);
        assertTrue("Candidate inserted ", foundCandidate.getName().equals(name));
        assertTrue("Candidate inserted ", foundCandidate.getCompetitionId().equals(competition.getId()));

    }

    @Test
    public void testFindCandidateById() {
        logger.info("Insert Candidate Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());
        mapper.insertOrUpdateCompetition(competition);
        List<Competition> competitions = mapper.findAllCompetitions();
        competition = competitions.get(0);

        String name = "Vasya Pupkin";
        Candidate candidate = new Candidate(name,competition.getId());

        mapper.insertOrUpdateCandidate(candidate);
        Candidate foundCandidate = mapper.findCandidateByName(name);
        Candidate candidateById = mapper.findCandidateById(foundCandidate.getId());

        assertTrue("Candidate found by id", candidateById.getId().equals(foundCandidate.getId()));

    }


    @Test
    public void testFindCandidatesByCompetitionId() {
        logger.info("Find Candidates By Competition id Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());
        mapper.insertOrUpdateCompetition(competition);
        List<Competition> competitions = mapper.findAllCompetitions();
        competition = competitions.get(0);

        String name = "Vasya Pupkin";
        Candidate candidate = new Candidate(name,competition.getId());
        mapper.insertOrUpdateCandidate(candidate);

        String name1 = "Vasya Pupkin1";
        Candidate candidate1 = new Candidate(name,competition.getId());
        mapper.insertOrUpdateCandidate(candidate1);

        String name2 = "Vasya Pupkin2";
        Candidate candidate2 = new Candidate(name,competition.getId());
        mapper.insertOrUpdateCandidate(candidate2);

        List<Candidate> candidates = mapper.findCandidatesByCompetitionId(competition.getId());
        assertTrue("Candidate inserted ", candidates.size()==3);

    }


    @Test
    public void testVote(){
        logger.info("Insert Vote Test");
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());
        mapper.insertOrUpdateCompetition(competition);
        List<Competition> competitions = mapper.findAllCompetitions();
        competition = competitions.get(0);

        String name = "Vasya Pupkin";
        Candidate candidate = new Candidate(name,competition.getId());
        mapper.insertOrUpdateCandidate(candidate);
        candidate = mapper.findCandidateByName(name);

        Vote vote = new Vote(candidate.getId(),competition.getId());
        mapper.insertOrUpdateVote(vote);

        List<Vote> votesComp = mapper.findVotesForCompetition(competition.getId());
        assertTrue("Vote inserted and found", votesComp.size()==1);

        List<Vote> votesCand = mapper.findVotesForCandidate(name,competition.getId());
        assertTrue("Vote inserted and found", votesCand.size()==1);

    }



}
