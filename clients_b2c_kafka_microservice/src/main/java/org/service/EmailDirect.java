package org.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.model.Email;
import org.model.EmailDirectt;
import org.model.EmailPlan;
import org.model.test;
import org.repisotory.EmailDirectsRepository;
import org.repisotory.EmailPlanRepository;
import org.repisotory.EmailRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;

import java.util.List;

@ApplicationScoped
public class EmailDirect {


    @Inject
    EmailRepository emailRepository;
    @Inject
    EmailPlanRepository emailPlanRepository;
    @Inject
    EmailDirectsRepository emailDirectsRepository;
    @Inject
    @Channel("mail-outt")
    Emitter<Record<String, EmailPlan>> emitteer;


    public List<Email> sendTo(Email mails) {

        int numFilters = 0;

        if (mails.getAge() != 0) {
            numFilters++;
        }

        if (mails.getGender() != null && !mails.getGender().isEmpty()) {
            numFilters++;
        }

        if (mails.getCity() != null && !mails.getCity().isEmpty()) {
            numFilters++;
        }
        EmailPlan filteredData = new EmailPlan();


        if (numFilters == 1) {
            if (mails.getAge() != 0) {
                List<Email> d =emailRepository.find("age", mails.getAge()).list();
                 filteredData.setMail(d);

            } else if (mails.getGender() != null && !mails.getGender().isEmpty()) {
                List<Email> d=emailRepository.find("gender", mails.getGender()).list();
                filteredData.setMail(d);
            } else {
                List<Email> d=emailRepository.find("city", mails.getCity()).list();
                filteredData.setMail(d);
            }
        } else if (numFilters == 2) {
            // Apply two filters
            if (mails.getAge() != 0 && mails.getGender() != null && !mails.getGender().isEmpty()) {
                List<Email> d=emailRepository.find("age  = ?1 and gender = ?2", mails.getAge(), mails.getGender()).list();
                filteredData.setMail(d);
            } else if (mails.getAge() != 0 && mails.getFilter().getCity() != null && !mails.getCity().isEmpty()) {
                List<Email> d=emailRepository.find(" age  = ?1 and city = ?2 ", mails.getAge(), mails.getCity()).list();
                filteredData.setMail(d);
            } else {
                List<Email> d=emailRepository.find("city = ?1 and gender = ?2 ", mails.getCity(), mails.getGender()).list();
                filteredData.setMail(d);
            }
        } else {
            List<Email> d=emailRepository.find("city = ?1 and gender = ?2 and age = ?3", mails.getCity(), mails.getGender(), mails.getFilter().getAge()).list();
            // Apply all three filters
            filteredData.setMail(d);
        }
        Log.info(filteredData);
if(mails.getDatee() !=null) {
    filteredData.setObjet(mails.getObjet());
    filteredData.setMessage(mails.getMessage());
    filteredData.setFilter(mails.getFilter());
    filteredData.setUser(mails.getUser());
    filteredData.setEmail(mails.getMail());
    filteredData.setDate(mails.getDatee());

    emailPlanRepository.persist(filteredData);

}else{

    EmailDirectt emailDirect = new EmailDirectt();
    emailDirect.setMessage(mails.getMessage());
    emailDirect.setUser(mails.getUser());
    emailDirect.setUserM(mails.getUserM());
    emailDirectsRepository.persist(emailDirect);
    filteredData.setObjet(mails.getObjet());
    filteredData.setMessage(mails.getMessage());
    for (Email m:filteredData.getMail()) {
        filteredData.setEmail(m.getMail());
        Log.info(m.getMail());
    }

    emitteer.send(Record.of("filteredData.getEmail()", filteredData));

}
            return null;

    }
    @Inject

    @Channel("mail-plan")
   Emitter<Record<String,EmailPlan>> emitter1;
  //@Scheduled(every = "1s")
   public void test(){
      LocalDate currentDate = LocalDate.now();

         List<EmailPlan> emails = emailPlanRepository.listAll();
       Log.info("hi0");
           for(EmailPlan mail:emails){
            if(mail.getDate().equals(currentDate)){
                emitter1.send(Record.of(mail.getEmail(),mail));
                Log.info("hi0");
            }}
               }
        }



