package org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.model.Enum.Size;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class B2B {

    private ObjectId id;
    private String campagne_name;
    private String campagne_mail;
    private String campagne_num;
    @Enumerated(EnumType.STRING)
    private Size size;
    private Date create_date;
    private User user;
    private String city;


}
