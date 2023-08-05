package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;
import org.model.B2BClients;
import org.model.Enum.Size;


import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class B2BEmailRepository implements PanacheMongoRepository<B2BClients> {



    public B2BClients findBySize(Size size) {
        return find("size", size).firstResult();
    }

    public B2BClients findByMail(String mail) {
        return find("mail", mail).firstResult();
    }

    public B2BClients findByNum(String num) {
        return find("num", num).firstResult();
    }

    public List<B2BClients> findByOwner(String user) {
        return find("user", user).list();
    }

    public List<B2BClients> sortedAscending(String value) {
        return listAll(Sort.by(value));
    }

    public List<B2BClients> sortedDescending(String value) {
        return listAll(Sort.by(value).descending());
    }

}
