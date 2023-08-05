package org.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;


import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;



import javax.enterprise.context.ApplicationScoped;


import java.time.LocalDate;

import java.util.Date;
import java.util.List;
@RegisterForReflection
@Data
@AllArgsConstructor
@ApplicationScoped

public class Email {

    @BsonId
    private ObjectId id;
    private String job;
    private String message;
    private String objet;
    private LocalDate datee;
    private LocalDate date;
    private String num;
    private int age;
    private String gender;
    private String city;
    private String name;
    private String mail;
    private Filter filter;
    private String url;
    private User user;
    private List<Email> mails;
    private UserM userM;



    public Email(){}
}
