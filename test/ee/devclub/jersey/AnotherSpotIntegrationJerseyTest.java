package ee.devclub.jersey;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static com.jayway.jsonassert.JsonAssert.with;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.junit.Assert.assertEquals;

//TODO: dedicated DB

public class AnotherSpotIntegrationJerseyTest extends JerseyTest {
    private Response response;



    @Test
    public void testGet() throws Exception {
        response = target("photo-spots").request().get();
        with(response.readEntity(String.class))
                .assertEquals("$.[0].name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.[0].id", 0);
    }

    @Test
    public void testId() throws Exception {
        response = target("photo-spots/id/0").request().get();
        with(response.readEntity(String.class))
                .assertEquals("$.name", "Kohtuotsa vaateplatvorm")
                .assertEquals("$.id", 0);
    }

    @Test
     public void testNotFound() throws Exception {
        response = target("photo-spots/id/100").request().get();
        assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void testWrongId() throws Exception {
        response = target("photo-spots/id/1a").request().get();
        assertEquals(NOT_FOUND, response.getStatus());
    }

    @Test
    public void testWrongId2() throws Exception {
        response = target("photo-spots/id/").request().get();
        assertEquals(NOT_FOUND, response.getStatus());
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
