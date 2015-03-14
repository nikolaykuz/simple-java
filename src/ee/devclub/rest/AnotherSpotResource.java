package ee.devclub.rest;

import ee.devclub.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Path("/another-spots")
@Produces(APPLICATION_JSON)
public class AnotherSpotResource extends SpringAwareResource {
    @Autowired SpringDataAnotherSpotRepository repo;
    int maxSpots = 1000;

    @GET
    public List<AnotherSpot> getAllSpots() {
      List<AnotherSpot> allSpots = repo.findAll();
      return allSpots.subList(0, Math.min(maxSpots, allSpots.size()));
    }

    @GET
    @Path("/ids/{id}")
    public AnotherSpot getSpotById(@PathParam("id") Long id) {
        return repo.findOne(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public AnotherSpot newPhotoSpot(@FormParam("name") String name, @FormParam("description") String description,
                                  @FormParam("lat") float lat, @FormParam("lon") float lon) {
        return repo.saveAndFlush(new AnotherSpot(name, description, new Location(lat, lon)));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public AnotherSpot newPhotoSpot(AnotherSpot anotherSpot) {
        return repo.saveAndFlush(anotherSpot);
    }
}
