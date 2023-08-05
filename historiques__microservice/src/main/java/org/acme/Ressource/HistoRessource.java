package org.acme.Ressource;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.Model.Historique;
import org.acme.Model.HistoriqueEnvoie;
import org.acme.Service.HistoService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/His")
public class HistoRessource {
    @Inject
    HistoService histoService;
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Historique historique) {
        histoService.save(historique);
        return Response.ok(historique).build();
    }
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List <Historique> get(){

        return   histoService.getAll();
    }
    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) {
        histoService.delete(id);
        return Response.ok().build();}

    @GET
    @Path("/All")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List <HistoriqueEnvoie> getALL(){

        return   histoService.getall();
    }

}
