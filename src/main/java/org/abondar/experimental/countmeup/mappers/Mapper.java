package org.abondar.experimental.countmeup.mappers;

import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Competition;
import org.abondar.experimental.countmeup.model.User;
import org.abondar.experimental.countmeup.model.Vote;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by abondar on 3/10/17.
 */
public interface Mapper {

    void insertOrUpdateUser(@Param("user") User user);
    void insertOrUpdateCompetition(@Param("competition")Competition competition);
    void insertOrUpdateVote(@Param("vote")Vote vote);
    void insertOrUpdateCandidate(@Param("candidate")Candidate candidate);
    User findUserByUserId(@Param("userId") String  userId);
    User findUserByToken(@Param("token") String  token);
    Competition findCompetitionById(@Param("id") Long id);
    Long findActiveCompetitionId();
    Competition findActiveCompetition();
    List<Competition> findAllCompetitions();
    Candidate findCandidateByName(@Param("name")String name);
    Candidate findCandidateById(@Param("id")Long id);
    List<Candidate> findCandidatesByCompetitionId(@Param("competitionId") Long competitionId);
    List<Vote> findVotesForCompetition(@Param("competitionId")Long competitionId);
    List<Vote> findVotesForCandidate(@Param("name") String name,
                                     @Param("competitionId")Long competitionId);
    void deleteAllVotes();
    void deleteAllCandidates();
    void deleteAllCompetitions();
    void deleteAllUsers();



}
