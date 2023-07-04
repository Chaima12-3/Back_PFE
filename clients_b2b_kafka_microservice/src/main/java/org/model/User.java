package org.model;

public class User {
    private String username;
    private String email;
    private Long countB ;

    private Long countBP ;


    public User(){}
    public User(String username, String email, Long countB, Long countBP) {
        this.username = username;
        this.email = email;
        this.countB = countB;
        this.countBP = countBP;

    }
    public Long getCountB() {
        return countB;
    }

    public void setCountB(Long countB) {
        this.countB = countB;
    }

    public Long getCountBP() {
        return countBP;
    }

    public void setCountBP(Long countBP) {
        this.countBP = countBP;
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
}
