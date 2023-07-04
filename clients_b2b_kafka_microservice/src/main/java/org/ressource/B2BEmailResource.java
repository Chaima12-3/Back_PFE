package org.ressource;

import com.opencsv.exceptions.CsvException;
import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.model.B2BClients;
import org.model.User;
import org.service.B2BMail;
import org.model.Enum.Size;
import org.model.Filter1;
import org.service.B2BEmailService;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/B2B")
@ApplicationScoped
public class B2BEmailResource {

    @Inject
    B2BEmailService b2BEmailService;
    @Inject
    B2BMail b2BMail;
    @GET
    @Path("/getAll")
    public List<B2BClients> getEmail() {
        return b2BEmailService.getAll();
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(B2BClients email) {
        b2BEmailService.addEmail(email);
        return Response.ok(email).build();
    }
    @POST
    @Path("/countP")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> countP(ArrayList<User> user){
        return b2BEmailService.countP(user);
    }
   @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response send(B2BClients b2BClients) {
       b2BMail.sendTo(b2BClients);

        return Response.accepted().build();
    }

    @POST
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(Filter1 filter){
        Log.info(filter.getUser());
        List<B2BClients> items =  b2BEmailService.getAll(filter);
        return Response.ok(items).build();
    }
    @POST
    @Path("/csv")
    @Consumes(MediaType.APPLICATION_JSON)
    public void importCSVData(B2BClients b2B) throws IOException, CsvException {
        b2BEmailService.extractCSVFromJSON(b2B);
    }
    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response test( String user ){
        Log.info(user);
        return Response.ok( b2BEmailService.get(user)).build();
    }
    @GET
    @Path("/{size}")
    public Response find(@PathParam("size") Size size) {
        B2BClients size1 =  b2BEmailService.getSize(size);
        return Response.ok(size1).build();
    }
    @GET
    @Path("/{mail}")
    public Response findMail(@PathParam("mail")String mail) {
        B2BClients B3 =  b2BEmailService.getMail(mail);
        return Response.ok(B3).build();
    }
    @GET
    @Path("/{num}")
    public Response findNum(@PathParam("num")String num) {
        B2BClients B2 =  b2BEmailService.getNum(num);
        return Response.ok(B2).build();
    }

    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") String id , B2BClients b2B){
        b2BEmailService.edit(id, b2B);
        return Response.ok(b2B).build();
    }
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") String id ){
        b2BEmailService.delete(id);
        return Response.ok().build();
    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getId/{id}")
    public B2BClients getItem(@PathParam("id") String id){
        return  b2BEmailService.getItem(id);
    }
}
