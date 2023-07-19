package org.ressource;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.model.B2BClient;
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

    @Incoming("b2bs")
    public void receivee(Record<String, EmailsPlan> record) {

        EmailsPlan emailContent = record.value();
        List<String> mails = Arrays.asList(emailContent.getEmail());
            try {

                Mail mail = new Mail()
                        .setSubject(emailContent.getObjet())
                        .setTo(mails)
                        .setHtml(emailContent.getMessage());
                mailer.send(mail).subscribeAsCompletionStage();
            }catch (Exception ex){
                ex.printStackTrace();

            }

    }


    @Incoming("mailb2bs")
    public void send(Record<String, EmailsPlan> record) {

        EmailsPlan emailContent = record.value();
        List<String> mails = Arrays.asList(emailContent.getEmail());
        try {

            Mail mail = new Mail()
                    .setSubject(emailContent.getObjet())
                    .setTo(mails)
                    .setText(emailContent.getMessage());
            mailer.send(mail).subscribeAsCompletionStage();
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }}








