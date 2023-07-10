package org.ressource;


import io.quarkus.logging.Log;

import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.annotations.RegisterForReflection;


import org.model.Email;
import org.model.Filter1;
import org.model.User;
import org.service.EmailDirect;
import org.service.EmailService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/B2C")
@ApplicationScoped
public class EmailResource {
    @Inject
    Mailer mailer;
    @Inject
    EmailService emailService;
    @Inject
    EmailDirect emailDirect;




    @POST
    @Path("/countP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> countP(ArrayList<User> user){
        return emailService.countP(user);
    }

   @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response send(Email email) {
       Log.info(email);
       emailDirect.sendTo(email);
        return Response.ok().build();
    }

    @POST
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(Filter1 filter){
        List<Email> items = emailService.getAll(filter);
        Log.info(items);
        return Response.ok(items).build();
    }

    @POST
    @Path("/csv")
    @Consumes(MediaType.APPLICATION_JSON)
    public void importCSVData(Email b2C)  {
        Log.info(b2C);
        emailService.extractCSVFromJSON(b2C);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Email b2c) {
        emailService.add(b2c);
        return Response.ok(b2c).build();
    }
    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") String id , Email b2C){
        emailService.editClient(id, b2C);
        return Response.ok(b2C).build();
    }
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") String id ){
        emailService.delete(id);
        return Response.ok().build();
    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getId/{id}")
    public Email getItem(@PathParam("id") String id){
        return emailService.getItem(id);
    }
}
