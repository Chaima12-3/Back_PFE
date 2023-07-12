package of.acme;

public class User {

    private String username;
    private String email;
    private String password;
    private Mail mail;

    public User(String username, String email, String password, Mail mail) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mail = mail;
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
