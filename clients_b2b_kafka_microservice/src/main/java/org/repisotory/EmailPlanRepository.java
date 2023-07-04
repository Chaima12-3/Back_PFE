package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.model.EmailPlans;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailPlanRepository implements PanacheMongoRepository<EmailPlans> {
}
