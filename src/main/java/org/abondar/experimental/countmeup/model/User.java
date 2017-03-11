package org.abondar.experimental.countmeup.model;

/**
 * Created by abondar on 3/10/17.
 */
public class User {

    private Long id;
    private String userId;
    private String token;
    private int voteAttempts;

    public User(String userId, String token) {
        this.userId = userId;
        this.token = token;
        this.voteAttempts = 0;
    }

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getVoteAttempts() {
        return voteAttempts;
    }

    public void setVoteAttempts(int voteAttempts) {
        this.voteAttempts = voteAttempts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", voteAttempts=" + voteAttempts +
                '}';
    }
}
