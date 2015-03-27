package ee.devclub.rest;

import ee.devclub.model.AnotherSpot;
import ee.devclub.model.Location;
import ee.devclub.model.SpringDataAnotherSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

//TODO: return 201 on create, set location header. Return Response?
//http://stackoverflow.com/questions/2342579/http-status-code-for-update-and-delete

@Singleton
@Path("/another-spots")
@Produces(APPLICATION_JSON)
@Component
public class AnotherSpotResource {
    @Autowired SpringDataAnotherSpotRepository repo;
    int maxSpots = 1000;

    @GET
    public List<AnotherSpot> getAllSpots() {
      List<AnotherSpot> allSpots = repo.findAll();
      return allSpots.subList(0, Math.min(maxSpots, allSpots.size()));
    }

    @GET
    @Path("/id/{id}")
    public Response getSpotById(@PathParam("id") Long id) {
        //TODO: location vs contentLocation
        return Response.ok(repo.findOne(id)).build();
    }

    //TODO: which POST is good/bad?
    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public AnotherSpot newPhotoSpot(@FormParam("name") String name, @FormParam("description") String description,
                                  @FormParam("lat") float lat, @FormParam("lon") float lon) {
        return repo.saveAndFlush(new AnotherSpot(name, description, new Location(lat, lon)));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public AnotherSpot newPhotoSpot(AnotherSpot anotherSpot) {
        return repo.saveAndFlush(anotherSpot);
    }
}
