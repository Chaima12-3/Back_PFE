package org.model;

public class User {

    private String userName;

    private String userEmail;

    private Long count ;

    private Long countP ;
    public User(){}

    public User(String userName, String userEmail, Long count, Long countP) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.count = count;
        this.countP = countP;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
