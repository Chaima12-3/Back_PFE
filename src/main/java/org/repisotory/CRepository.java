package org.repisotory;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

import org.model.Campagnes;


import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CRepository implements PanacheMongoRepository<Campagnes> {




}
