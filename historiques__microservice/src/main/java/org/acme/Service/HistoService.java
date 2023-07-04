package org.acme.Service;

import org.acme.Model.Historique;
import org.acme.Model.HistoriqueEnvoie;
import org.acme.Repository.HistoEvoieRepository;
import org.acme.Repository.HistoRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.IsoChronology;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class HistoService {
    @Inject
    HistoRepository histoRepository;
    @Inject
    HistoEvoieRepository histoEvoieRepository;
    public Response save(Historique historique){

         HashMap<String, String> hashMap = new HashMap<>();
        LocalDateTime date =  LocalDateTime.now();
        LocalDate Date = LocalDate.now();
        String str2 = String.valueOf(Date);
        int hour = date.getHour();
        int minute = date.getMinute();

        String dateM = str2+" " + hour + ":"+minute;
        String his = "Objet :"+" "+historique.getSubject()+" "+"Message :"+" "+historique.getMessage();
        hashMap.put(his, dateM);
        Historique his1 = new Historique();
        histoRepository.persist(his1);
        return Response.ok().build();
    }
    public Response delete(String id) {
       Historique historique = histoRepository.findById(new ObjectId(id));
        try {
            histoRepository.delete(historique);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {

            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    public List <Historique> getAll(){

        return histoRepository.listAll();
    }

    public Response Delete(String id) {
        HistoriqueEnvoie historique = histoEvoieRepository.findById(new ObjectId(id));
        try {
            histoEvoieRepository.delete(historique);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {

            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    public List <HistoriqueEnvoie> getall(){

        return histoEvoieRepository.listAll();
    }

}
