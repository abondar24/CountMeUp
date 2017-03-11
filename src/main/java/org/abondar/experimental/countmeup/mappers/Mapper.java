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

    void inserOrUpdatetUser(@Param("user") User user);
    void insertOrUpdateCompetition(@Param("competition")Competition competition);
    void insertVote(@Param("vote")Vote vote);
    void insertOrUpdateCandidate(@Param("candidate")Candidate candidate);
    User findUserById(@Param("id") Long id);
    Competition findCompetitionById(@Param("id") Long id);
    List<Vote> findVotesForCompetition(@Param("competition_id")Long competition_id);
    List<Candidate> findCandidatesByIds(@Param("candIds") List<Long> candIdList);
    List<Vote> findVotesForCandidate(@Param("candidate") Long candidate,
                                    @Param("competition_id")Long competition_id);
    void deleteAllVotes();
    void deleteAllCandidates();
    void deleteAllCompetitions();



}
