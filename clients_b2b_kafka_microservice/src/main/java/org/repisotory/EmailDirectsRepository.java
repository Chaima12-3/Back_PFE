package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.model.EmailDirects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class EmailDirectsRepository implements PanacheMongoRepository<EmailDirects> {
}
