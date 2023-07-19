package org.acme.Model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.util.Date;
import java.util.HashMap;

@Data
public class Historique {
    @BsonId
    private ObjectId id;
    private String UserName;
    private String UserEmail;
    private String subject;
    private String message;


    public Historique(String userName, String subject, String message, String dateM) {
        UserName = userName;

        this.subject = subject;
        this.message = message;
        this.dateM = dateM;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
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

    public String getDateM() {
        return dateM;
    }

    public void setDateM(String dateM) {
        this.dateM = dateM;
    }

    private String dateM ;

    private HashMap<String, String> hashMap ;

    public Historique() {
    }
}
