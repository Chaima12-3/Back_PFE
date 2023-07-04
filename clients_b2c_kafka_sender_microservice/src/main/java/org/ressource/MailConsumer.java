package org.ressource;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.mailer.runtime.BlockingMailerImpl;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;

import org.model.Emails;
import org.service.EmailService;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;


@ApplicationScoped
public class MailConsumer {

    @Inject
     ReactiveMailer mailer;





    @Incoming("mail-in")

    public void receive( Record<String, Emails> record) {
        Emails emailContent = record.value();
        List<String> recipients = Arrays.asList(record.key());

        System.out.println("recipients = " + recipients);

     try {
         Mail mail = new Mail()
                 .setSubject("Joyeux Anniversaire")
                 .setTo(recipients)
                 .setText("Joyeux anniversaire"+" "+emailContent.getName()+" "+ "! Que cette journée soit remplie de bonheur et de joie.");
         mailer.send(mail).subscribeAsCompletionStage();
     }catch (Exception ex){
         System.err.println("------------ = " + ex);

     }


    }


}
