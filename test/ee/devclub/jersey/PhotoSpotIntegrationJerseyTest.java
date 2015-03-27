package ee.devclub.jersey;

import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.rest.PhotoSpotResource;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.jayway.jsonassert.JsonAssert.with;
import static javax.ws.rs.core.Response.Status.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//TODO: assert status, header (location, media-type), body

//TODO: dedicated DB, who puts data in the DB now??
//TODO: set Accept-Header with xml, and other formats
//TODO: not successful cases for PUT and POST

//TODO: make generic test?

//TODO: rest assured tool to use

//TODO: recreate or not? Note: DB_CLOSE_DELAY

public class PhotoSpotIntegrationJerseyTest extends BaseIntegrationTest {
    private final static String NON_EXISTENT_ID_PATH="/id/100500";

    public PhotoSpotIntegrationJerseyTest() {
        super(PhotoSpotResource.ROOT_PATH);
    }

    @Test
    public void testGetRoot() throws Exception {
        with(assertOk(httpGet("")).readEntity(String.class))
                .assertEquals("$.[0].name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.[0].id", 0);
    }

    @Test
    public void testGetId() throws Exception {
        with(httpGet("/id/0").readEntity(String.class))
                .assertEquals("$.name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.id", 0);
    }

    @Test
     public void testGetNotFound() throws Exception {
        assertNotFound(httpGet(NON_EXISTENT_ID_PATH));
        assertNotFound(httpGet("/id/1a"));
        assertNotFound(httpGet("/id/"));
        assertNotFound(httpGet("/id"));
        assertNotFound(httpGet("/i"));
        assertNotFound(httpGet("/0"));
        assertNotFound(httpGet("/id//0"));
    }

    @Test
    public void testPost() throws Exception {
        PhotoSpot photoSpot = new PhotoSpot("Test name", "Test description", new Location(10, 10));
        Response response = assertStatus(CREATED, httpPostJSON(photoSpot));
        //TODO: location
        assertEquals("Wrong photo spot created", photoSpot, fromResponse(response));
    }

    @Test
    public void testPut() throws Exception {
        PhotoSpot photoSpot = new PhotoSpot("Test name", "Test description", new Location(10, 10));
        PhotoSpot created = fromResponse(assertStatus(CREATED, httpPostJSON(photoSpot)));
        assertEquals("Wrong photo spot created", photoSpot, created);

        photoSpot.setName("Modified test name");
        photoSpot.setDescription("Modified test description");
        PhotoSpot updated = fromResponse(assertStatus(OK, httpPut("/id/" + created.getId(), photoSpot)));

        assertEquals(photoSpot, updated);
        assertEquals(created.getId(), updated.getId());
    }

    @Test
    public void testDeleteId() throws Exception {
        fail();
    }

    @Test
    public void testDeleteIdNotFound() throws Exception {
        assertNotFound(httpDelete(NON_EXISTENT_ID_PATH));
    }

    @Test
    public void testDeleteRootNotAllowed() throws Exception {
        assertStatus(METHOD_NOT_ALLOWED, httpDelete(""));
    }

    @Test
    public void testHeadRoot() throws Exception {
        assertOk(httpHead(""));
    }

    public static PhotoSpot fromResponse(Response response) {
        return response.readEntity(PhotoSpot.class);
    }
}
