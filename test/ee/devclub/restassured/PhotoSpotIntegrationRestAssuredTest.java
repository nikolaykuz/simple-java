package ee.devclub.restassured;

import com.jayway.restassured.response.Response;
import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.rest.PhotoSpotResource;
import org.junit.Test;

import static com.jayway.restassured.http.ContentType.JSON;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class PhotoSpotIntegrationRestAssuredTest extends BaseIntegrationTest {

    public PhotoSpotIntegrationRestAssuredTest() {
        super(PhotoSpotResource.ROOT_PATH);
    }

    //TODO: drop DB after each test. Flag in base class?

    @Test
    public void testPost() throws Exception {
        PhotoSpot photoSpotAtClient = new PhotoSpot("Test name", "Test description", new Location(11, 12));
        Response response = newRequest().content(photoSpotAtClient).post().then().extract().response();

        response.then().assertThat()
                    .statusCode(CREATED)
                    .contentType(JSON)
                    .header(LOCATION, startsWith(getBaseUri().toString()))
                    .body("name", equalTo("Test name"),
                            "description", equalTo("Test description"),
                            "location.latitude", equalTo(11f),
                            "location.longitude", equalTo(12f)
                    );

        PhotoSpot photoSpotAtServer = response.body().as(PhotoSpot.class);
        assertEquals("Client and server entities do not match", photoSpotAtClient, photoSpotAtServer);
        assertEquals("Header location and entity ids do not match", photoSpotAtServer.getId(), locationId(response));
    }

    @Test
    public void testGet() throws Exception {
        newRequest().get("/id/1").then().assertThat().statusCode(OK);
    }

    public static Long locationId(Response response) {
        String location = response.header(LOCATION);
        String id = location.substring(location.lastIndexOf('/') + 1);
        return Long.valueOf(id);
    }
}
