package org.model;

public class UserM {

    private String username;
    private String email;
    private Long count ;

    private Long countP ;
    private Long countC ;
    public UserM(String username, String email, Long count, Long countP, Long countC) {
        this.username = username;
        this.email = email;
        this.count = count;
        this.countP = countP;
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
