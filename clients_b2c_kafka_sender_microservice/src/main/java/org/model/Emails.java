package org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.model.Enum.Behavior;
import org.model.Enum.Categories;
import org.model.Enum.Size;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor

@ApplicationScoped
public class Emails {

    private ObjectId id;
    private String job;
    private String message;
    private String objet;
    private LocalDate datee;

    private LocalDate date;
    private String num;
    private String age;
    private String gender;
    private String city;
    private String name;
    private String mail;
    private Filter filter;
    private String url;
    private User user;



    public Emails(){}
}
