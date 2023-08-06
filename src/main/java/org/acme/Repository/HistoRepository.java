package org.acme.Repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.Model.Historique;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoRepository implements PanacheMongoRepository<Historique> {
}
