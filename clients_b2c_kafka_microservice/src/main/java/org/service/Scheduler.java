package org.service;


import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.Record;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.model.Email;
import org.repisotory.EmailRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class Scheduler {
    @Inject
    EmailRepository emailRepository;

    @Inject
    @Channel("mail-out")
    Emitter<Record<String, Email>> emitter;
    //@Scheduled(every = "10s")
    void sendToKafka() {
        {
            LocalDate currentDate = LocalDate.now();

            List<Email> emails = emailRepository.listAll();


            for(Email mail:emails){
                if(mail.getDate().getDayOfMonth()==currentDate.getDayOfMonth()&&mail.getDate().getMonth()==currentDate.getMonth()){
            emitter.send(Record.of(mail.getMail(),mail));
            Log.info("hi0");
        }}}
    }

}
