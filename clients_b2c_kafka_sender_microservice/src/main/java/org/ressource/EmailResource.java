package org.ressource;

import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.annotations.RegisterForReflection;

import org.model.Emails;
import org.model.Filter;
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
    @Inject
    Emails email;
    @GET
    @Path("/getAll")
    public List<Emails> getEmail() {
        return emailService.getAll();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Emails email) {
        emailService.addEmail(email);
        return Response.ok(email).build();
    }

    @POST
    @Path("/addd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addd(Filter filter) {
        emailService.addF(filter);
        return Response.ok(filter).build();
    }


}
