package of.acme;

public class Mail {
    private String subject;
    private String message;
    private User user;

    public Mail(String subject, String message, User user) {
        this.subject = subject;
        this.message = message;
        this.user = user;
    }
	public Mail(){}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
