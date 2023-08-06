package org.acme;

import io.quarkus.security.User;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin/realms/{realm}/users")
@RegisterRestClient(configKey = "keycloak-api")
@ApplicationScoped
@Default
public interface KeycloakService {

    @GET
    @Path("/all")
    @Produces("application/json")
    List<KeycloakUser> getUsers(@PathParam("realm") String realm, @QueryParam("max") int max);
    @GET
    @Produces("application/json")
    KeycloakUser get(String id);

    @GET

    long count();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response addUser(KeycloakUser user);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void deleteUser(@PathParam("id") String id);


}
