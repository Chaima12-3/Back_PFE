package org.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RegisterForReflection
@Data
@AllArgsConstructor
@ApplicationScoped
@ToString
public class B2BClients {

    @BsonId
    private ObjectId id;
    private String objet;
    private String message;
    private String campagne_name;
    private String campagne_mail;
    private String campagne_num;
    private String pay;
    private String create_date;
    private User user;
    private String size;
    private Filter filter;
    private String url;
    private UserM userM;
    private LocalDate datee;
    private List<B2BClients> b2BClientsList;
    public B2BClients(){}
}
