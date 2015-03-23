package ee.devclub.jersey;

import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.rest.PhotoSpotResource;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.jayway.jsonassert.JsonAssert.with;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//TODO: dedicated DB, who puts data in the DB now??
//TODO: POST, PUT
//TODO: set Accept-Header with xml, and other formats

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
        Response response = assertStatus(CREATED, httpPost(photoSpot));
        assertEquals(photoSpot, response.readEntity(PhotoSpot.class));
        //TODO: assert location
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

    //TODO: seems to be bad, if new field is added need to find this place and add
    public void assertPhotoSpot(PhotoSpot expected, PhotoSpot actual) {
        assertEquals("", expected.getName(), actual.getName());
        assertEquals("", expected.getDescription(), actual.getDescription());
        assertEquals("", expected.getLocation(), actual.getLocation());
    }
}
