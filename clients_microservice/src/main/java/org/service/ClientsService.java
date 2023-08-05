package org.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.quarkus.logging.Log;
import org.bson.types.ObjectId;
import org.model.B2C;
import org.model.Clients;
import org.model.Enum.Behavior;
import org.model.Filter;

import org.repisotory.ClientsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClientsService {

    @Inject
    ClientsRepository ClientsRepository;
    public List<Clients> getAll(Filter filters) {
        List<Clients> clientsList = new ArrayList<>();
        List<Clients> B2CList = new ArrayList<>();
        Clients[] clients = ClientsRepository.listAll().toArray(new Clients[0]);
        List<Clients> res = null;
        List<Clients> searchSop;
        List<Clients> searchB2C;

        try {
            for (Clients client : clients) {
                if (filters.getFilter() != null && !filters.getFilter().isEmpty() && filters.getFilter().equals("B2B")) {
                    if (client.getB2B() != null) {
                        clientsList.add(client);
                    }
                } else if (client.getB2C() != null){
                    B2CList.add(client);
                }
            }
            if (filters.getSearch()  != null && !filters.getSearch().isEmpty()) {
                searchSop = clientsList.stream()
                        .filter(item -> item.getB2B().getCampagne_name().toLowerCase().contains(filters.getSearch().toLowerCase())
                               ).collect(Collectors.toList());
            }else {
                searchSop = clientsList;
            }
            if (filters.getSearch()  != null && !filters.getSearch().isEmpty()) {
                searchB2C = clientsList.stream()
                        .filter(item -> item.getB2C().getName().toLowerCase().contains(filters.getSearch().toLowerCase())
                                 ).collect(Collectors.toList());
            }else {
                searchB2C = B2CList;
            }
            res = filters.getFilter() != null && !filters.getFilter().isEmpty() && filters.getFilter().equals("B2B") ? searchSop : searchB2C;
        } catch (Exception e) {
            System.out.println(e);
            // Handle any exceptions that may occur while fetching workflows from your data source
            e.printStackTrace();
        }
        Log.info(res);
        return res;
    }
    public Response save(Clients clients){
        ClientsRepository.persist(clients);
        return Response.ok().build();
    }
    public Clients getItem(String id){ return ClientsRepository.findById(new ObjectId(id));}
    public long getCount(){ return ClientsRepository.count();}
    public Response delete(String id){
        Clients clients = getItem(id);
        try{
            ClientsRepository.delete(clients);
            return Response.status(Response.Status.ACCEPTED).build();
        }catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}


