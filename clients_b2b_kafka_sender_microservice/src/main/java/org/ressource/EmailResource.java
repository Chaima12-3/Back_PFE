package org.ressource;

import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.model.B2BClient;

import org.service.EmailService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/email")
@ApplicationScoped
public class EmailResource {
    @Inject
    Mailer mailer;
    @Inject
    EmailService emailService;

    @GET
    @Path("/getAll")
    public List<B2BClient> getEmail() {
        return emailService.getAll();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(B2BClient email) {
        emailService.addEmail(email);
        return Response.ok(email).build();
    }




}
