package org.acme.Repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.Model.Historique;
import org.acme.Model.HistoriqueEnvoie;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HistoEvoieRepository implements PanacheMongoRepository<HistoriqueEnvoie> {
}
