package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.model.Filter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FilterRepository implements PanacheMongoRepository<Filter> {
}
