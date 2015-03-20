package ee.devclub.jersey;

import ee.devclub.rest.PhotoSpotResource;
import org.junit.Test;

import static com.jayway.jsonassert.JsonAssert.with;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

//TODO: dedicated DB

/**
 * @author nkuznetsov
 * @since 16.03.2015
 */
public class PhotoSpotIntegrationJerseyTest extends BaseIntegrationTest {

    public PhotoSpotIntegrationJerseyTest() {
        super(PhotoSpotResource.PATH);
    }

    @Test
    public void testGet() throws Exception {
        with(getAndAssertStatus(OK).readEntity(String.class))
                .assertEquals("$.[0].name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.[0].id", 0);
    }

    @Test
    public void testId() throws Exception {
        with(get("/id/0").readEntity(String.class))
                .assertEquals("$.name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.id", 0);
    }

    @Test
     public void testGetNotFound() throws Exception {
        getAndAssertStatus("/id/100", NOT_FOUND);
        getAndAssertStatus("/id/1a", NOT_FOUND);
        getAndAssertStatus("/id/", NOT_FOUND);
    }

    @Test
    public void testDelete() throws Exception {
        response = target("photo-spots/id/15").request().delete();
        assertEquals(204, response.getStatus());
    }
    //TODO: POST, DELETE, PUT


    @Test
    public void testHead() throws Exception {
        response = target("photo-spots").request().head();
        assertEquals(500, response.getStatus());
    }
}
