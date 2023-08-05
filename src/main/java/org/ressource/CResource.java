package org.ressource;

import com.opencsv.exceptions.CsvException;
import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;


import org.model.Campagnes;
import org.model.Enum.Behavior;

import org.model.Filter;
import org.model.User;

import org.service.CService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterForReflection
@Path("/channels")
public class CResource {

    @Inject
    CService CService;


    @POST
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response get(Filter filter){
        List<Campagnes> items = CService.getAll(filter);
        return Response.ok(items).build();
    }



    @POST
    @Path("/csv")
    @Consumes(MediaType.APPLICATION_JSON)
    public void importCSVData(Campagnes campagnes) throws IOException, CsvException {
        Log.info(campagnes);
        CService.extractCSVFromJSON(campagnes);
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Campagnes campagnes) {
        CService.add(campagnes);
        return Response.ok(campagnes).build();
    }
    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") String id , Campagnes campagnes){
        CService.edit(id, campagnes);
        return Response.ok(campagnes).build();
    }
    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") String id ){
        CService.delete(id);
        return Response.ok().build();
    }
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getId/{id}")
    public Campagnes getItem(@PathParam("id") String id){
        return CService.getItem(id);
    }

}
