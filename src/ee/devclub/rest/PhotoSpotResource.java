package ee.devclub.rest;

import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.model.PhotoSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Path(PhotoSpotResource.PATH)
@Produces(APPLICATION_JSON)
@Component
public class PhotoSpotResource {
    public static final String PATH = "/photo-spots";

    @Autowired PhotoSpotRepository repo;
    int maxSpots = 1000;

    @GET
    public List<PhotoSpot> getAllSpots() {
      List<PhotoSpot> allSpots = repo.getAllSpots();
      return allSpots.subList(0, Math.min(maxSpots, allSpots.size()));
    }

    @GET
    @Path("/id/{id}")
    public PhotoSpot getSpotById(@PathParam("id") Long id) {
        return repo.getSpotById(id);
    }

    @POST
    public PhotoSpot newPhotoSpot(@FormParam("name") String name, @FormParam("description") String description,
                                  @FormParam("lat") float lat, @FormParam("lon") float lon) {
        return repo.persist(new PhotoSpot(name, description, new Location(lat, lon)));
    }

    @DELETE
    @Path("/id/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        repo.delete(id);
        return Response.noContent().build();
    }
}
