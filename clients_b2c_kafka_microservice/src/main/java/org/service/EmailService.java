package org.service;


import io.quarkus.logging.Log;
import org.bson.types.ObjectId;
import org.model.Email;


import org.model.EmailPlan;
import org.model.Filter1;
import org.model.User;
import org.repisotory.EmailPlanRepository;
import org.repisotory.EmailRepository;


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

public class EmailService {

    @Inject
    EmailRepository emailRepository;
    @Inject
    EmailPlanRepository emailPlanRepository;


    public Response add(Email email) {

        emailRepository.persist(email);
        Log.info("test");
        return Response.status(Response.Status.CREATED).build();
    }




    public Response delete(String id) {
        Email b2C = emailRepository.findById(new ObjectId(id));
        try {
            emailRepository.delete(b2C);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public List<User> countP(ArrayList<User> user) {
        List<User> usersWithCount = new ArrayList<>();

        for (User u : user) {
            List<EmailPlan> resP = emailPlanRepository.find("user.userEmail", u.getUserEmail()).list();
            List<Email> res = emailRepository.find("user.userEmail",u.getUserEmail()).list();
            long countP = resP.stream().count();
            long count = res.stream().count();
            User userWithCount = new User();
            userWithCount.setUserEmail(u.getUserEmail());
            userWithCount.setUserName(u.getUserName());
            userWithCount.setCount(count);
            userWithCount.setCountP(countP);

            usersWithCount.add(userWithCount);
        }

        return usersWithCount;
    }



    public Response editClient(String id, Email b2C) {
        Email b2C1 = emailRepository.findById(new ObjectId(id));
        try {
            b2C1.setMail(b2C.getMail() != null ? b2C.getMail() : b2C1.getMail());
            b2C1.setName(b2C.getName() != null ? b2C.getName() : b2C1.getName());
            b2C1.setNum(b2C.getNum() != null ? b2C.getNum() : b2C1.getNum());
            b2C1.setAge(b2C.getAge() != 0 ? b2C.getAge() : b2C1.getAge());
            b2C1.setGender(b2C.getGender() != null ? b2C.getGender() : b2C1.getGender());
            b2C1.setCity(b2C.getCity() != null ? b2C.getCity() : b2C1.getCity());
            b2C1.setJob(b2C.getJob() != null ? b2C.getJob() : b2C1.getJob());

            emailRepository.update(b2C1);
            return Response.ok(b2C1).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Email getItem(String id) {
        return emailRepository.findById(new ObjectId(id));
    }

    public List<Email> getAll(Filter1 filters) {
        List<Email> bs = null, sorted = null;
        List<Email> res = null;

        if (filters.getUser().getUserEmail() != null && !filters.getUser().getUserEmail().isEmpty()) {
            res = emailRepository.streamAll()
                    .filter(item -> item.getUser().getUserEmail().toLowerCase().contains(filters.getUser().getUserEmail().toLowerCase())
                    ).collect(Collectors.toList());
            if (filters.getFilter() != null && !filters.getFilter().isEmpty()) {
                bs = res.stream()
                        .filter(item -> item.getName().toLowerCase().contains(filters.getFilter().toLowerCase())
                        ).collect(Collectors.toList());
            } else {
                bs = res;
            }
            if (filters.getSearch() != null && !filters.getSearch().isEmpty()) {
                sorted = bs.stream()
                        .filter(item -> item.getName().toLowerCase().contains(filters.getSearch().toLowerCase())


                        ).collect(Collectors.toList());
            } else {
                sorted = bs;
            }

            // Sorting
            if (filters.getSort() != null && filters.getSort().getField() != null) {
                String fieldName = filters.getSort().getField();
                boolean isAscending = filters.getSort().getOrder().equals("ascend");

                switch (fieldName) {
                    case "name":
                        sorted.sort(Comparator.comparing(item -> item.getName(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "mail":
                        sorted.sort(Comparator.comparing(item -> item.getMail(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "num":
                        sorted.sort(Comparator.comparing(item -> item.getNum(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "city":
                        sorted.sort(Comparator.comparing(item -> item.getCity(), String.CASE_INSENSITIVE_ORDER));
                        break;

                    case "gender":
                        sorted.sort(Comparator.comparing(item -> item.getGender(), String.CASE_INSENSITIVE_ORDER));
                        break;
                    case "job":
                        sorted.sort(Comparator.comparing(item -> item.getJob(), String.CASE_INSENSITIVE_ORDER));
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


    public void extractCSVFromJSON(Email b2C) {
        try {


            String fileURL = b2C.getUrl();

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

                    Email b2c = new Email();
                    b2c.setAge(Integer.parseInt(fields[0]));
                    b2c.setMail(fields[1]);
                    b2c.setNum(fields[2]);
                    b2c.setName(fields[3]);
                    b2c.setJob(fields[4]);
                    b2c.setCity(fields[5]);
                    b2c.setGender(fields[6]);
                    //b2c.setUser(b2C.getUser());
                    // b2c.setUser(user.setUserEmail(fields[8]));
                    b2c.setUser(b2C.getUser());
                    emailRepository.persist(b2c);

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
