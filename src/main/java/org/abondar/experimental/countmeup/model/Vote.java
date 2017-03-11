package org.abondar.experimental.countmeup.model;

/**
 * Created by abondar on 3/10/17.
 */
public class Vote {

    private Long id;
    private String candidate;
    private Long competitionId;

    public Vote(String candidate, Long competitionId) {
        this.candidate = candidate;
        this.competitionId = competitionId;
    }

    public Vote(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
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
                ", candidate='" + candidate + '\'' +
                ", competitionId=" + competitionId +
                '}';
    }
}
