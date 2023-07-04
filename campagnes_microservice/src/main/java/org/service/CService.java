package org.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.bson.types.ObjectId;

import org.model.Campagnes;
import org.model.Enum.Behavior;
import org.model.Filter;
import org.model.User;

import org.repisotory.CRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CService {

    @Inject
    CRepository cRepository;

    public List<Campagnes> get() {
        return cRepository.listAll();
    }



    public Response add(Campagnes campagnes) {

        cRepository.persist(campagnes);
        return Response.status(Response.Status.CREATED).build();
    }

    public Response delete(String id) {
        Campagnes campagnes = cRepository.findById(new ObjectId(id));
        try {
            cRepository.delete(campagnes);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response edit(String id, Campagnes campagnes) {
        Campagnes campagnes1 = cRepository.findById(new ObjectId(id));
        try {
            campagnes1.setChannelsNames(campagnes.getChannelsNames() != null ? campagnes.getChannelsNames() : campagnes1.getChannelsNames());
            campagnes1.setDescription(campagnes.getDescription() != null ? campagnes.getDescription() : campagnes1.getDescription());
            campagnes1.setCommunicationChannels(campagnes.getCommunicationChannels() != null ? campagnes.getCommunicationChannels() : campagnes1.getCommunicationChannels());
           campagnes1.setDate(campagnes.getDate() != null ? campagnes.getDate(): campagnes1.getDate());


            cRepository.update(campagnes1);
            return Response.ok(campagnes1).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



    public List<Campagnes> getAll(Filter filters) {
        List<Campagnes> bs = null, sorted = null;
        List<Campagnes> res = null;

        if (filters.getUser().getUserEmail() != null && !filters.getUser().getUserEmail().isEmpty()) {
            res = cRepository.streamAll()
                    .filter(item -> item.getUser().getUserEmail().toLowerCase().contains(filters.getUser().getUserEmail().toLowerCase())
                    ).collect(Collectors.toList());
            if (filters.getFilter() != null && !filters.getFilter().isEmpty()) {
                bs = res.stream()
                        .filter(item -> item.getChannelsNames().toLowerCase().contains(filters.getFilter().toLowerCase())
                        ).collect(Collectors.toList());
            } else {
                bs = res;
            }
            if (filters.getSearch() != null && !filters.getSearch().isEmpty()) {
                sorted = bs.stream()
                        .filter(item -> item.getChannelsNames().toLowerCase().contains(filters.getSearch().toLowerCase())


                        ).collect(Collectors.toList());
            } else {
                sorted = bs;
            }

            // Sorting
            if (filters.getSort() != null && filters.getSort().getField() != null) {
                String fieldName = filters.getSort().getField();
                boolean isAscending = filters.getSort().getOrder().equals("ascend");

                switch (fieldName) {
                    case "channelsNames":
                        sorted.sort(Comparator.comparing(item -> item.getChannelsNames(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "communicationChannels":
                        sorted.sort(Comparator.comparing(item -> item.getCommunicationChannels(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "Descriptions":
                        sorted.sort(Comparator.comparing(item -> item.getDescription(), String.CASE_INSENSITIVE_ORDER));
                        break;

                    default:
                        break;
                }

                if (!isAscending) {
                    Collections.reverse(sorted);
                }
            }


        }
        return sorted;

    }

    public void extractCSVFromJSON(Campagnes campagnes) {
        try {


            String fileURL = campagnes.getUrl();

            URL url = new URL(fileURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    // Create a new B2C entity

                    Campagnes campagnes1 = new Campagnes();
                    campagnes1.setChannelsNames(fields[0]);
                    campagnes1.setCommunicationChannels(fields[1]);
                    campagnes1.setDescription(fields[2]);
                    campagnes1.setDate(fields[3]);

                    campagnes1.setUser(campagnes.getUser());
                    cRepository.persist(campagnes);

                    // Persist the entity

                }
                reader.close();
            } else {
                // Handle error response
                System.out.println("Failed to fetch the CSV file. Response Code: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            // Handle exception
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public Campagnes getItem(String id) {
        return cRepository.findById(new ObjectId(id));
    }
}


