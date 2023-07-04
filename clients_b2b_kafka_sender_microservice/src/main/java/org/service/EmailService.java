package org.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import org.Repository.B2BEmailRepository;
import org.model.B2BClient;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped

public class EmailService {

    @Inject
    B2BEmailRepository b2BEmailRepository;

    @Inject
    Mailer mailer;
    public Response addEmail(B2BClient email) {

        b2BEmailRepository.persist(email);
        return Response.status(Response.Status.CREATED).build();
    }

    public List<B2BClient> getAll() {
        return b2BEmailRepository.listAll();
    }

    @Inject
    ReactiveMailer reactiveMailer;

    public void sendMail(String email, String message,String object)
    {
        reactiveMailer.send(Mail.withText(email,object,message));

    }
    public void sendEmail(List<String> recipient, String subject, String body) {
        Mail mail = new Mail()
                .setTo(recipient)
                .setSubject(subject)
                .setHtml(body);

        mailer.send(mail);
    }
}
