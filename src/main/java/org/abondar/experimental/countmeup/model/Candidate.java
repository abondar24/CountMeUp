package org.abondar.experimental.countmeup.model;

/**
 * Created by abondar on 3/10/17.
 */
public class Candidate {
    private Long id;
    private String name;
    private Long competition_id;

    public Candidate(String name, Long competition_id) {
        this.name = name;
        this.competition_id = competition_id;
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

    public Long getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(Long competition_id) {
        this.competition_id = competition_id;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", competition_id=" + competition_id +
                '}';
    }
}
