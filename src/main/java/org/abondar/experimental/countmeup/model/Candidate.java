package org.abondar.experimental.countmeup.model;

/**
 * Created by abondar on 3/10/17.
 */
public class Candidate {
    private Long id;
    private String name;
    private Long competitionId;

    public Candidate(){}

    public Candidate(String name, Long competitionId) {
        this.name = name;
        this.competitionId = competitionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", competitionId=" + competitionId +
                '}';
    }
}
