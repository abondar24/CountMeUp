package org.abondar.experimental.countmeup.model;


public class Vote {

    private Long id;
    private Long candidateId;
    private Long competitionId;

    public Vote(Long candidateID, Long competitionId) {
        this.candidateId = candidateID;
        this.competitionId = competitionId;
    }

    public Vote(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateID) {
        this.candidateId = candidateID;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", candidateID=" + candidateId +
                ", competitionId=" + competitionId +
                '}';
    }
}
