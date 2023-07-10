package org.model;

public class User {

    private String userName;

    private String userEmail;

    private Long count ;

    private Long countP ;
    public User(){}

    public User(String username, String email, Long count, Long countP) {
        this.userName = username;
        this.userEmail = email;
        this.count = count;
        this.countP = countP;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
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
