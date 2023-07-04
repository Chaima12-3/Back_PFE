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
import java.util.List;

@ApplicationScoped
public class scheduler {

    @Inject
    ReactiveMailer mailer;

    @Incoming("mail-inn")
    public void receivee(Record<Message, EmailsPlan> record) {
        System.out.println("scheduler.receivee");
            EmailsPlan emailContent = record.value();
            Message emails=record.key();
            Log.info(emails);
            for(Emails email:emails.getEmails()){
                List<String> mails=List.of(email.getMail());

                Log.info(emailContent);
                try {

                    Mail mail = new Mail()
                            .setSubject(emailContent.getObjet())
                            .setTo(mails)
                            .setHtml(emailContent.getMessage());
                    mailer.send(mail).subscribeAsCompletionStage();
                }catch (Exception ex){
                    ex.printStackTrace();

                }}

    }


    @Incoming("mail-plan")
    public void send(Record<Message, EmailsPlan> record) {
        System.out.println("scheduler.receivee");
        EmailsPlan emailContent = record.value();
        Message emails=record.key();
        Log.info(emails);
        for(Emails email:emails.getEmails()){
            List<String> mails=List.of(email.getMail());

            Log.info(emailContent);
            try {

                Mail mail = new Mail()
                        .setSubject(emailContent.getObjet())
                        .setTo(mails)
                        .setHtml(emailContent.getMessage());
                mailer.send(mail).subscribeAsCompletionStage();
            }catch (Exception ex){
                ex.printStackTrace();

            }}

    }

    }









