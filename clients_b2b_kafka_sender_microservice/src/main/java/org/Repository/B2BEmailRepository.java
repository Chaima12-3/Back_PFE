package org.Repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.model.B2BClient;


import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@ApplicationScoped
public class B2BEmailRepository implements PanacheMongoRepository<B2BClient> {


}
