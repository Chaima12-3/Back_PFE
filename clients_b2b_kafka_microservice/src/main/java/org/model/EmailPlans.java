package org.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RegisterForReflection
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class EmailPlans {
    @BsonId
    private ObjectId id;
    private String message;
    private String objet;
    private Filter filter;
    private User user;
    private String email;
    private UserM userM;
    private List<B2BClients> b2BClients;
    private LocalDate datee;
}
