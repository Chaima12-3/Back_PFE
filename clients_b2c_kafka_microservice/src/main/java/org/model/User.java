package org.model;

public class User {

    private String username;

    private String email;

    private Long count ;

    private Long countP ;
    public User(){}

    public User(String username, String email, Long count, Long countP) {
        this.username = username;
        this.email = email;
        this.count = count;
        this.countP = countP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCountP() {
        return countP;
    }

    public void setCountP(Long countP) {
        this.countP = countP;
    }
}
