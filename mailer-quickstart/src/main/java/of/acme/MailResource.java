package of.acme;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/mails")
public class MailResource {

    @Inject
    Mailer mailer;
    @POST
    @Path("/sendmail")
    @Consumes(MediaType.APPLICATION_JSON)
    @Blocking
    public void sendEmail(User user) {
        mailer.send(Mail.withText(user.getUserEmail(), user.getMail().getSubject(), user.getMail().getMessage()));
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Blocking
    public void send(User user) {
        String objet ="Activation Account";
        mailer.send(Mail.withText(user.getUserEmail(), objet, user.getMail().getMessage()));
    }
    @Inject
    ReactiveMailer reactiveMailer;

    @GET
    @Path("/reactive")
    public Uni<Void> sendEmailUsingReactiveMailer() {
        return reactiveMailer.send(
                Mail.withText("benbrahim_nidhal@outlook.com",
                        "Ahoy from Quarkus",
                        "A simple email sent from a Quarkus application using the reactive API."
                )
        );
    }

}