package org.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;

import org.model.Emails;
import org.model.Filter;
import org.repisotory.EmailRepository;
import org.repisotory.FilterRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped

public class EmailService {

    @Inject
    EmailRepository emailRepository;
    @Inject
    FilterRepository filterRepository;
    @Inject
    Mailer mailer;
    public Response addEmail(Emails email) {

        emailRepository.persist(email);
        return Response.status(Response.Status.CREATED).build();
    }

    public List<Emails> getAll() {
        return emailRepository.listAll();
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
    public Response addF(Filter filter) {

        filterRepository.persist(filter);
        return Response.status(Response.Status.CREATED).build();
    }
}
