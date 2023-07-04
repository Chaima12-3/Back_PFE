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

@RegisterForReflection
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class Historique {
    @BsonId
    private ObjectId id;
    private String UserName;
    private String UserEmail;
    private String subject;
    private String message;





   private String dateM ;

    private HashMap<String, String> hashMap ;


}
