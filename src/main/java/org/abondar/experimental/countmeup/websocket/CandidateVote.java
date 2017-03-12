package org.abondar.experimental.countmeup.websocket;


public class CandidateVote {

    private String name;
    private Integer votes;


    public CandidateVote(String name, Integer votes) {
        this.name = name;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" + " name:'" + name  + " votes:" + votes + "}";
    }
}