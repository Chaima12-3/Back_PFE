package org.ressource;

import com.opencsv.exceptions.CsvException;
import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.annotations.RegisterForReflection;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.model.*;
import org.model.Enum.Behavior;

import org.service.ClientsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/clients")
public class ClientsResource {

    @Inject
    ClientsService clientsService;
    @Inject
    Mailer mailer;
    @POST
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clients> getALL(Filter filter) {
        Log.info(filter.getFilter());
        return clientsService.getAll(filter);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clients getItem(@PathParam("id") String id) {
        return clientsService.getItem(id);
    }

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Long count() {
        return clientsService.getCount();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(B2C b2C) {
        Clients clients = new Clients();
        clients.setB2C(b2C);
        clientsService.save(clients);
        return Response.ok(clients).build();
    }

    @POST
    @Path("/add-B2B")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(B2B b2B) {
        Clients workflow = new Clients();
        workflow.setB2B(b2B);
        clientsService.save(workflow);
        return Response.ok(workflow).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") String id) {
        clientsService.delete(id);
        return Response.ok().build();
    }
    @POST
    @Path("/sendmail")
    @Consumes(MediaType.APPLICATION_JSON)

    public void sendEmail(UserM user) {
        mailer.send(Mail.withText(user.getUserEmail(), user.getSubject(), user.getMessage()));
    }
}
