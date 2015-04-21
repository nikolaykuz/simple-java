package ee.devclub.rest;

import ee.devclub.model.PhotoSpot;
import ee.devclub.repo.PhotoSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.validation.executable.ExecutableType.ALL;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Singleton
@Path(PhotoSpotResource.ROOT_PATH)
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Component
@ValidateOnExecution(type = ALL)
public class PhotoSpotResource {
    public static final String ROOT_PATH = "/photo-spots";
    public static final String ID_SUB_PATH = "id"; //TODO: use it, Curie??

    @Autowired
    PhotoSpotRepository repo;
    int maxSpots = 1000;

    @GET
    public List<PhotoSpot> getAllSpots() {
      List<PhotoSpot> allSpots = repo.findAll();
      return allSpots.subList(0, Math.min(maxSpots, allSpots.size()));
    }

    @GET
    @Path("/id/{id}")
    public PhotoSpot getSpotById(@Min(value = 0) @PathParam("id") Long id) {
        return repo.findOne(id);
    }

    @PUT
    @Path("/id/{id}")
    public Response updatePhotoSpot(@Min(value = 0) @PathParam("id") Long id,
                                    @NotNull @Valid PhotoSpot photoSpotParam)
    {
        PhotoSpot photoSpot = repo.findOne(id);
        if (photoSpot == null)
            throw new DataRetrievalFailureException("No photospot with id " + id + " exists");
        photoSpotParam.setId(photoSpot.getId());
        photoSpot = repo.save(photoSpotParam);
        return Response.ok(photoSpot).build();
    }

    @POST
    public Response newPhotoSpot(@NotNull @Valid PhotoSpot photoSpotParam) {
        PhotoSpot photoSpot = repo.save(photoSpotParam);
        //TODO: how to construct proper URI, right now it is ugly
        return Response.created(URI.create("id/" + photoSpot.getId())).entity(photoSpot).build();
    }

    @DELETE
    @Path("/id/{id}")
    public Response deleteById(@Min(value = 0) @PathParam("id") Long id) {
        repo.delete(id);
        return Response.noContent().build();
    }
}
