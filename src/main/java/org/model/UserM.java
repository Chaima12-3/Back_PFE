package org.model;

public class UserM {

    private String username;
    private String email;
    private Long countB ;

    private Long countBP ;
    private Long countC ;

    public UserM(String username, String email, Long countB, Long countBP,Long countC) {
        this.username = username;
        this.email = email;
        this.countB = countB;
        this.countBP = countBP;
        this.countC=countC;
    }

    public Long getCountC() {
        return countC;
    }

    public void setCountC(Long countC) {
        this.countC = countC;
    }

    public UserM(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserM() {
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
