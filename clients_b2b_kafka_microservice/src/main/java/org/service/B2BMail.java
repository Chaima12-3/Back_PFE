package org.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.model.B2BClients;
import org.model.EmailDirects;
import org.model.EmailPlans;
import org.model.test;
import org.repisotory.B2BEmailRepository;
import org.repisotory.EmailDirectsRepository;
import org.repisotory.EmailPlanRepository;
import org.ressource.B2BEmailResource;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class B2BMail {


    @Inject
    B2BEmailRepository b2BEmailRepository;
    @Inject
    EmailDirectsRepository emailDirectsRepository;


    @Inject
    EmailPlanRepository emailPlanRepository;
    @Inject
    @Channel("b2b")
    Emitter<Record<String, EmailPlans>> emitterr;


    public List<B2BClients> sendTo(B2BClients b2BClients) {

        int numFilters = 0;

        if (b2BClients.getPay() != null && !b2BClients.getPay().isEmpty()) {
            numFilters++;
        }

        if (b2BClients.getSize() != null && !b2BClients.getSize().isEmpty()) {
            numFilters++;
        }
        EmailPlans filteredData = new EmailPlans();


        if (numFilters == 1) {
            List<B2BClients> d;
            if (b2BClients.getPay() != null && !b2BClients.getPay().isEmpty()) {
                d = b2BEmailRepository.find("pay", b2BClients.getPay()).list();

            } else {
                d = b2BEmailRepository.find("size", b2BClients.getSize()).list();
            }
            filteredData.setB2BClients(d);

        } else {
            List<B2BClients> d=b2BEmailRepository.find("pay = ?1 and size = ?2 ", b2BClients.getPay(),b2BClients.getSize()).list();
            filteredData.setB2BClients(d);
        }
        Log.info(filteredData);


        if(b2BClients.getDatee() !=null) {
            filteredData.setObjet(b2BClients.getObjet());
            filteredData.setMessage(b2BClients.getMessage());

            filteredData.setUser(b2BClients.getUser());
            filteredData.setB2BClients(b2BClients.getB2BClientsList());
            filteredData.setDatee(b2BClients.getDatee());

            emailPlanRepository.persist(filteredData);




    }else{

        EmailDirects emailDirect = new EmailDirects();
        emailDirect.setMessage(b2BClients.getMessage());
        emailDirect.setUser(b2BClients.getUser());
        emailDirect.setUserM(b2BClients.getUserM());
        emailDirectsRepository.persist(emailDirect);
        filteredData.setObjet(b2BClients.getObjet());
        filteredData.setMessage(b2BClients.getMessage());
        for (B2BClients m:filteredData.getB2BClients()) {
            filteredData.setEmail(m.getCampagne_mail());
            Log.info(m.getCampagne_mail());
        }


        emitterr.send(Record.of("filteredData.getEmail()", filteredData));

    }
        return null;
    }
    @Inject
    @Channel("mailb2b")
    Emitter<Record<String, EmailPlans>> emitter2;

   //@Scheduled(every = "10s")
    public void test1() {
       LocalDate currentDate = LocalDate.now();

       List<EmailPlans> emails = emailPlanRepository.listAll();
       Log.info("hi0");
     for(EmailPlans mail:emails){
          if(mail.getDatee().equals(currentDate)){
              emitter2.send(Record.of("mail.getMail()",mail));
           }}}
   }
