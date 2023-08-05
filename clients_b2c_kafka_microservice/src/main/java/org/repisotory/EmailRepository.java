package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import org.model.Email;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EmailRepository implements PanacheMongoRepository<Email> {
    public Email findById(String id){return find("id",id).firstResult();}

    public List<Email> sortedAscending(String value) {
        return listAll(Sort.by(value));
    }
    public List<Email> sortedDescending(String value) {
        return listAll(Sort.by(value).descending());
    }

    public List<Email> findByGender(String gender) {
        return find("gender", gender).list();
    }



}
