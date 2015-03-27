package ee.devclub.rest;

import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.model.PhotoSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Path(PhotoSpotResource.ROOT_PATH)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Component
public class PhotoSpotResource {
    public static final String ROOT_PATH = "/photo-spots";
    public static final String ID_SUB_PATH = "id"; //TODO: use it, Curie??

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

    @PUT
    @Path("/id/{id}")
    public Response putPhotoSpot(@PathParam("id") Long id, PhotoSpot photoSpotParam) {
        PhotoSpot photoSpot = repo.getSpotById(id);
        photoSpotParam.setId(photoSpot.getId());
        photoSpot = repo.persist(photoSpotParam);
        return Response.ok(photoSpot).build();
    }

    //TODO: delete later
    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response newPhotoSpot(@FormParam("name") String name, @FormParam("description") String description,
                                 @FormParam("latitude") float lat, @FormParam("longitude") float lon) {
        PhotoSpot photoSpot = repo.persist(new PhotoSpot(name, description, new Location(lat, lon)));
        //TODO: how to construct proper URI
        return Response.created(URI.create("id/" + photoSpot.getId())).entity(photoSpot).build();
    }

    @POST
    public Response newPhotoSpot(PhotoSpot photoSpotParam) {
        //TODO: validate!? XXE
        PhotoSpot photoSpot = repo.persist(photoSpotParam);
        //TODO: how to construct proper URI
        return Response.created(URI.create("id/" + photoSpot.getId())).entity(photoSpot).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response deleteById(@PathParam("id") Long id) {
        repo.delete(id);
        return Response.noContent().build();
    }
}
