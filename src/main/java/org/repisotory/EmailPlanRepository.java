package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

import org.model.EmailPlan;


import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class
EmailPlanRepository implements PanacheMongoRepository<EmailPlan> {
}
