package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

import org.model.Emails;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@ApplicationScoped
public class EmailRepository implements PanacheMongoRepository<Emails> {

    public Emails findByDate(Date date){ return find("date",date).firstResult();}
}
