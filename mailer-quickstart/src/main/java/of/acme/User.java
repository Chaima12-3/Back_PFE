package of.acme;

public class User {

    private String userName;
    private String userEmail;
    private String password;

    public User(String userName, String userEmail, String password, Mail mail) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.mail = mail;
    }

    private Mail mail;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public  User(){}


}
