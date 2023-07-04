package org.service;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.reactive.messaging.kafka.Record;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.model.Email;
import org.model.EmailPlan;
import org.model.test;
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
    @Channel("mail-outt")
    Emitter<Record<test, EmailPlan>> emitteer;


    public List<Email> sendTo(Email mails) {

        int numFilters = 0;

        if (mails.getFilter().getAge() != 0) {
            numFilters++;
        }

        if (mails.getFilter().getGender() != null && !mails.getFilter().getGender().isEmpty()) {
            numFilters++;
        }

        if (mails.getFilter().getCity() != null && !mails.getFilter().getCity().isEmpty()) {
            numFilters++;
        }
        EmailPlan filteredData = new EmailPlan();
        test d = new test();

        if (numFilters == 1) {
            if (mails.getFilter().getAge() != 0) {
                d.setEmails(emailRepository.find("age", mails.getFilter().getAge()).list());
                 filteredData.setMail(d);

            } else if (mails.getFilter().getGender() != null && !mails.getFilter().getGender().isEmpty()) {
                d.setEmails(emailRepository.find("gender", mails.getFilter().getGender()).list());
                filteredData.setMail(d);
            } else {
                d.setEmails(emailRepository.find("city", mails.getFilter().getCity()).list());
                filteredData.setMail(d);
            }
        } else if (numFilters == 2) {
            // Apply two filters
            if (mails.getFilter().getAge() != 0 && mails.getFilter().getGender() != null && !mails.getFilter().getGender().isEmpty()) {
               d.setEmails(emailRepository.find("age  = ?1 and gender = ?2", mails.getFilter().getAge(), mails.getFilter().getGender()).list());
                filteredData.setMail(d);
            } else if (mails.getFilter().getAge() != 0 && mails.getFilter().getCity() != null && !mails.getFilter().getCity().isEmpty()) {
             d.setEmails(emailRepository.find(" age  = ?1 and city = ?2 ", mails.getFilter().getAge(), mails.getFilter().getCity()).list());
                filteredData.setMail(d);
            } else {
                d.setEmails(emailRepository.find("city = ?1 and gender = ?2 ", mails.getFilter().getCity(), mails.getFilter().getGender()).list());
                filteredData.setMail(d);
            }
        } else {
            d.setEmails(emailRepository.find("city = ?1 and gender = ?2 and age = ?3", mails.getFilter().getCity(), mails.getFilter().getGender(), mails.getFilter().getAge()).list());
            // Apply all three filters
            filteredData.setMail(d);
        }
if(mails.getFilter().getDate()!=null) {
    filteredData.setObjet(mails.getObjet());
    filteredData.setMessage(mails.getMessage());
    filteredData.setFilter(mails.getFilter());
    filteredData.setUser(mails.getUser());
    Log.info(filteredData);
    emailPlanRepository.persist(filteredData);

}else{
    filteredData.setObjet(mails.getObjet());
    filteredData.setMessage(mails.getMessage());

    Log.info(filteredData);
    emitteer.send(Record.of(filteredData.getMail(), filteredData));

}
            return null;

    }
    @Inject

    @Channel("mail-plan")
   Emitter<Record<test,EmailPlan>> emitter1;
   //@Scheduled(every = "10s")
   public void test(){
      LocalDate currentDate = LocalDate.now();

         List<EmailPlan> emails = emailPlanRepository.listAll();
       Log.info("hi0");
           for(EmailPlan mail:emails){
            if(mail.getFilter().getDate().getDayOfMonth()==currentDate.getDayOfMonth()&&mail.getFilter().getDate().getMonth()==currentDate.getMonth()){
                emitter1.send(Record.of(mail.getMail(),mail));
                Log.info("hi0");
            }}
               }
        }



