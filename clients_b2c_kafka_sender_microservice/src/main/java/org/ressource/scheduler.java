package org.ressource;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;

import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.model.Emails;
import org.model.EmailsPlan;
import org.model.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class scheduler {

    @Inject
    ReactiveMailer mailer;

    @Incoming("mail-inn")
    public void receivee(Record<String, EmailsPlan> record) {
        Log.info(record.value());
        System.out.println("scheduler.receivee");
            EmailsPlan emailContent = record.value();
           List<String> emails = Arrays.asList(emailContent.getEmail());



                try {

                    Mail mail = new Mail()
                            .setSubject(emailContent.getObjet())
                            .setTo(emails)
                            .setHtml(emailContent.getMessage());
                    mailer.send(mail).subscribeAsCompletionStage();
                }catch (Exception ex){
                    ex.printStackTrace();

                }

    }


    @Incoming("mail-plan")
    public void send(Record<String, EmailsPlan> record) {
        System.out.println("scheduler.receivee");
        EmailsPlan emailContent = record.value();
        List<String> emails = Arrays.asList(record.key());
        Log.info(emails);

            try {

                Mail mail = new Mail()
                        .setSubject(emailContent.getObjet())
                        .setTo(emails)
                        .setHtml(emailContent.getMessage());
                mailer.send(mail).subscribeAsCompletionStage();
            }catch (Exception ex){
                ex.printStackTrace();

            }

    }

    }









