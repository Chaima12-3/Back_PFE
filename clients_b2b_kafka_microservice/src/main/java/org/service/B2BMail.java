package org.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.model.B2BClients;
import org.model.EmailPlans;
import org.model.test;
import org.repisotory.B2BEmailRepository;
import org.repisotory.EmailPlanRepository;


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
    EmailPlanRepository emailPlanRepository;
    @Inject
    @Channel("b2b")
    Emitter<Record<test, EmailPlans>> emitterr;


    public List<B2BClients> sendTo(B2BClients b2BClients) {

        int numFilters = 0;

        if (b2BClients.getFilter().getPay() != null && !b2BClients.getFilter().getPay().isEmpty()) {
            numFilters++;
        }

        if (b2BClients.getFilter().getSize() != null && !b2BClients.getFilter().getSize().isEmpty()) {
            numFilters++;
        }
        EmailPlans filteredData = new EmailPlans();
        test d = new test();

        if (numFilters == 1) {
            if (b2BClients.getFilter().getPay() != null && !b2BClients.getFilter().getPay().isEmpty()) {
                d.setEmails(b2BEmailRepository.find("age", b2BClients.getFilter().getPay()).list());
                filteredData.setMail(d);

            } else {
                d.setEmails(b2BEmailRepository.find("gender", b2BClients.getFilter().getSize()).list());
                filteredData.setMail(d);
            }

        } else {
            d.setEmails(b2BEmailRepository.streamAll()
                    .filter(item -> item.getSize().toLowerCase().contains(b2BClients.getFilter().getSize().toLowerCase())
                            && item.getPay().toLowerCase().contains(b2BClients.getFilter().getPay().toLowerCase())
                    ).collect(Collectors.toList()));
            filteredData.setMail(d);
        }
        if(b2BClients.getFilter().getDate()!=null) {
            filteredData.setObjet(b2BClients.getObjet());
            filteredData.setMessage(b2BClients.getMessage());
            filteredData.setFilter(b2BClients.getFilter());
            filteredData.setUser(b2BClients.getUser());
            Log.info(filteredData);
            emailPlanRepository.persist(filteredData);

        }else{
            filteredData.setObjet(b2BClients.getObjet());
            filteredData.setMessage(b2BClients.getMessage());
            emitterr.send(Record.of(filteredData.getMail(), filteredData));

        }
        return null;

    }
    @Inject
    @Channel("mailb2b")
    Emitter<Record<test, EmailPlans>> emitter2;

   //@Scheduled(every = "10s")
    public void test1() {
       LocalDate currentDate = LocalDate.now();

       List<EmailPlans> emails = emailPlanRepository.listAll();
       Log.info("hi0");
     for(EmailPlans mail:emails){
          if(mail.getFilter().getDate().getDayOfMonth()==currentDate.getDayOfMonth()&&mail.getFilter().getDate().getMonth()==currentDate.getMonth()){
              emitter2.send(Record.of(mail.getMail(),mail));
           }}}
   }
