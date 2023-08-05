package org.model;

public class User {
    private String userName;
    private String userEmail;
    private Long countB ;

    private Long countBP ;


    public User(){}

    public User(String userName, String userEmail, Long countB, Long countBP) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.countB = countB;
        this.countBP = countBP;
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


}
