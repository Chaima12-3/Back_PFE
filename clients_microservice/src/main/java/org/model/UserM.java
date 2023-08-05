package org.model;

public class UserM {
    private String userName;
    private String userEmail;

    private String subject;
    private String message;

    public UserM(String userName, String userEmail, String subject, String message) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.subject = subject;
        this.message = message;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
