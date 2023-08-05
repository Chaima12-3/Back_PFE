package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.mailer.Mailer;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/users")
@RegisterForReflection
public class UserResource {

    @Inject
    @Default
    KeycloakService keycloakService;
    @Inject
    Mailer mailer;

    @GET
    @Path("/getAll")
    public List<KeycloakUser> getUsers() {
        String realm = "StagePFE";
        int max = 10;
        return keycloakService.getUsers(realm, max);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(KeycloakUser email) {
        keycloakService.addUser(email);
        return Response.ok(email).build();
    }
    @DELETE
    @Path("/delete/{id}")
    public void delete(@PathParam("id") String id){
         keycloakService.deleteUser(id);
    }

    @GET
    @Path("/count")
    public long countUsers(){
        return keycloakService.count();
    }
    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id){
        return Response.ok(keycloakService.get(id)).build();
    }


    }

