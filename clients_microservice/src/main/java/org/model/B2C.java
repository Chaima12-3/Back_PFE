package org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.model.Enum.Behavior;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B2C {

    private ObjectId id;
    private String mail;
    private String num;
    private User user;
    private String age;
    private String name;
    private String job;
    private String city;
    private String gender;



}
