package org.service;


import io.netty.channel.local.LocalEventLoopGroup;
import io.quarkus.logging.Log;
import org.bson.types.ObjectId;
import org.model.B2BClients;

import org.model.EmailPlans;
import org.model.Enum.Size;
import org.model.Filter1;
import org.model.User;
import org.repisotory.B2BEmailRepository;
import org.repisotory.EmailPlanRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
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

public class B2BEmailService {

    @Inject
    B2BEmailRepository b2BEmailRepository;
    @Inject
    EmailPlanRepository emailPlanRepository;


    public Response addEmail(B2BClients email) {

        b2BEmailRepository.persist(email);
        return Response.status(Response.Status.CREATED).build();
    }

    public List<B2BClients> getAll() {
        return b2BEmailRepository.listAll();
    }


    public List<B2BClients> get(String user) {
        return b2BEmailRepository.findByOwner(user);
    }

    public B2BClients getSize(Size size) {
        return b2BEmailRepository.findBySize(size);
    }

    public B2BClients getMail(String mail) {
        return b2BEmailRepository.findByMail(mail);
    }

    public B2BClients getNum(String num) {
        return b2BEmailRepository.findByNum(num);
    }

    public Response add(B2BClients b2B) {

        b2BEmailRepository.persist(b2B);
        return Response.status(Response.Status.CREATED).build();
    }

    public Response edit(String id, B2BClients b2B) {
        B2BClients b2B1 = b2BEmailRepository.findById(new ObjectId(id));
        try {
            b2B1.setCampagne_name(b2B.getCampagne_name() != null ? b2B.getCampagne_name() : b2B1.getCampagne_name());
            b2B1.setCampagne_mail(b2B.getCampagne_mail() != null ? b2B.getCampagne_mail() : b2B1.getCampagne_mail());
            b2B1.setCampagne_name(b2B.getCampagne_num() != null ? b2B.getCampagne_num() : b2B1.getCampagne_num());
            b2B1.setCreate_date(b2B.getCreate_date() != null ? b2B.getCreate_date() : b2B1.getCreate_date());
            b2B1.setPay(b2B.getPay() !=null ? b2B.getPay() : b2B1.getPay());
            b2B1.setUser(b2B.getUser()!=null?b2B.getUser():b2B1.getUser());

            b2BEmailRepository.update(b2B1);
            return Response.ok(b2B1).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response delete(String id) {
        B2BClients b2B = b2BEmailRepository.findById(new ObjectId(id));
        try {
            b2BEmailRepository.delete(b2B);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    public List<User> countP(ArrayList<User> user) {
        List<User> usersWithCount = new ArrayList<>();

        for (User u : user) {
            List<EmailPlans> resP = emailPlanRepository.find("user.userEmail", u.getEmail()).list();
            List<B2BClients> res = b2BEmailRepository.find("user.userEmail",u.getEmail()).list();
            long countBP = resP.stream().count();
            long countB = res.stream().count();
            User userWithCount = new User();
            userWithCount.setEmail(u.getEmail());
            userWithCount.setUsername(u.getUsername());
            userWithCount.setCountB(countB);
            userWithCount.setCountBP(countBP);


            usersWithCount.add(userWithCount);
        }
        return usersWithCount;
    }

    public B2BClients getItem(String id) {
        return b2BEmailRepository.findById(new ObjectId(id));
    }

    public List<B2BClients> getAll(Filter1 filters){
        List<B2BClients> bs = null,sorted = null;
        List<B2BClients> res = null;

        if(filters.getUser().getEmail() != null && !filters.getUser().getEmail().isEmpty()){
            res = b2BEmailRepository.streamAll()
                    .filter(item -> item.getUser().getEmail().toLowerCase().contains(filters.getUser().getEmail() .toLowerCase())
                    ).collect(Collectors.toList());
            if (filters.getFilter() != null && !filters.getFilter().isEmpty()) {
                bs = res.stream()
                        .filter(item -> item.getCampagne_name().toLowerCase().contains(filters.getFilter() .toLowerCase())
                        ).collect(Collectors.toList());
            } else {
                bs = res;
            }
            if (filters.getSearch()  != null && !filters.getSearch().isEmpty()) {
                sorted = bs.stream()
                        .filter(item -> item.getCampagne_name().toLowerCase().contains(filters.getSearch().toLowerCase())
                                || (item.getCampagne_mail() != null && item.getCampagne_mail().toLowerCase().contains(filters.getSearch() .toLowerCase()))
                                || (item.getCampagne_num() != null && item.getCampagne_num().toLowerCase().contains(filters.getSearch() .toLowerCase()))
                                || (item.getPay() != null && item.getPay().toLowerCase().contains(filters.getSearch() .toLowerCase()))

                        ).collect(Collectors.toList());
            }else {
                sorted = bs;
            }

            // Sorting
            if (filters.getSort() != null && filters.getSort().getField() != null) {
                String fieldName = filters.getSort().getField();
                boolean isAscending = filters.getSort().getOrder().equals("ascend");

                switch (fieldName) {
                    case "campagne_name":
                        sorted.sort(Comparator.comparing(item -> item.getCampagne_name(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "campagne_mail":
                        sorted.sort(Comparator.comparing(item -> item.getCampagne_mail(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "campagne_num":
                        sorted.sort(Comparator.comparing(item -> item.getCampagne_num(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "city":
                        sorted.sort(Comparator.comparing(item -> item.getPay(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    default:
                        break;
                }

                if (!isAscending) {
                    Collections.reverse(sorted);
                }
            }}
        return sorted;

    }


    public void extractCSVFromJSON(B2BClients b2B) {
        try {

            String fileURL = b2B.getUrl();

            URL url = new URL(fileURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");
                    User user = new User();
                    // Create a new B2C entity

                    B2BClients b2b = new B2BClients();
                    b2b.setCampagne_name(fields[0]);
                    b2b.setCampagne_mail(fields[1]);
                    b2b.setCampagne_num(fields[2]);
                    b2b.setPay(fields[3]);
                    b2b.setSize(fields[4]);
                    b2b.setCreate_date(fields[5]);

                    // b2c.setUser(user.setUserName(fields[7]));
                    // b2c.setUser(user.setUserEmail(fields[8]));
                    b2b.setUser(b2B.getUser());
                    b2BEmailRepository.persist(b2b);

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
}
