package ee.devclub.restassured;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import ee.devclub.rest.PhotoSpotResource;
import org.junit.Test;

import java.util.List;

import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.http.ContentType.XML;
import static ee.devclub.rest.PhotoSpotResource.ID_SUB_PATH;
import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static junit.framework.TestCase.assertEquals;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class PhotoSpotIntegrationRestAssuredTest extends BaseIntegrationTest {
    private final static PhotoSpot PHOTO_SPOT_1 = new PhotoSpot("Test name", "Test description", new Location(11, 12));
    private final static PhotoSpot PHOTO_SPOT_2 = new PhotoSpot("Test name 2", "Test description 2", new Location(12.1f, 13.5f));
    private final static String ID = ID_SUB_PATH + "/";

    public PhotoSpotIntegrationRestAssuredTest() {
        super(PhotoSpotResource.ROOT_PATH);
    }

    @Test
    public void testPost() throws Exception {
        createPhotoSpot(PHOTO_SPOT_1);
    }

    @Test
    public void testPostWrongBody() throws Exception {
        newRequest().content("Wrong content").post().then().assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void testPostBodyAllNulls() throws Exception {
        newRequest().content(new PhotoSpot()).post().then().assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void testPostValidated() throws Exception {
        newRequest().content(new PhotoSpot("1", "2", new Location(1, 1))).post().then().assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void testPostByIdFail() throws Exception {
        newRequest().content(PHOTO_SPOT_1).post(ID + 2).then().assertThat().statusCode(SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testGetRootEmpty() throws Exception {
        assertPhotoSpotsSize(0);
    }

    @Test
    public void testGetRoot() throws Exception {
        createPhotoSpot(PHOTO_SPOT_1);
        assertPhotoSpotsSize(1);

        createPhotoSpot(PHOTO_SPOT_2);
        assertPhotoSpotsSize(2);

        ValidatableResponse response = getPhotoSpotsAsResponse().then();
        assertBodyById(response, PHOTO_SPOT_1, 0l);
        assertBodyById(response, PHOTO_SPOT_2, 1l);
    }

    @Test
    public void testGetById() throws Exception {
        ValidatableResponse vResponse;
        PhotoSpot photoSpot = createPhotoSpot(PHOTO_SPOT_1);
        PhotoSpot photoSpot2 = createPhotoSpot(PHOTO_SPOT_2);
        vResponse = newRequest().get(ID + photoSpot.getId()).then().assertThat().statusCode(SC_OK).contentType(JSON);
        assertBody(vResponse, photoSpot);
        vResponse = newRequest().get(ID + photoSpot2.getId()).then().assertThat().statusCode(SC_OK).contentType(JSON);
        assertBody(vResponse, photoSpot2);
    }

    @Test
    public void testPutRootFail() throws Exception {
        newRequest().delete().then().assertThat().statusCode(SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testPutById() throws Exception {
        PhotoSpot photoSpot = createPhotoSpot(PHOTO_SPOT_1);
        Response response = newRequest().content(PHOTO_SPOT_2).put(ID + photoSpot.getId()).thenReturn();
        response.then().assertThat().statusCode(SC_OK);
        assertBody(response, PHOTO_SPOT_2);
        PhotoSpot photoSpot2 = response.body().as(PhotoSpot.class);
        assertEquals("Updated entity has wrong id", photoSpot.getId(), photoSpot2.getId());

        assertPhotoSpotsSize(1);
    }

    @Test
    public void testPutByIdFail() throws Exception {
        PhotoSpot photoSpot = createPhotoSpot(PHOTO_SPOT_1);
        Response response = newRequest().content(PHOTO_SPOT_2).put(ID + photoSpot.getId() + 1).thenReturn();
        response.then().assertThat().statusCode(SC_NOT_FOUND);

        newRequest().content(PHOTO_SPOT_2).put(ID + photoSpot.getId() + "z").then().assertThat().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void testDeleteById() throws Exception {
        PhotoSpot photoSpot = createPhotoSpot(PHOTO_SPOT_1);
        PhotoSpot photoSpot2 = createPhotoSpot(PHOTO_SPOT_2);
        assertPhotoSpotsSize(2);
        newRequest().delete(ID + photoSpot.getId()).then().assertThat().statusCode(SC_NO_CONTENT);
        assertPhotoSpotsSize(1);
        newRequest().delete(ID + photoSpot.getId()).then().assertThat().statusCode(SC_NOT_FOUND);

        newRequest().delete(ID + photoSpot2.getId()).then().assertThat().statusCode(SC_NO_CONTENT);
        assertPhotoSpotsSize(0);
    }

    @Test
    public void testDeleteRootFail() throws Exception {
        newRequest().delete().then().statusCode(SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void testGetXml() throws Exception {
        newRequest().header(ACCEPT, XML).get().then().assertThat().statusCode(SC_NOT_ACCEPTABLE);
    }

    private PhotoSpot createPhotoSpot(PhotoSpot photoSpot) {
        Response response = newRequest().content(photoSpot).post().thenReturn();
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .contentType(JSON)
                .header(LOCATION, startsWith(getBaseUri().toString()));
        assertBody(response, photoSpot);
        PhotoSpot photoSpotFromServer = response.body().as(PhotoSpot.class);
        assertEquals("Client and server entities do not match", photoSpot, photoSpotFromServer);
        assertEquals("Header location and entity ids do not match",
                photoSpotFromServer.getId(), retrieveIdFromLocationHeader(response)
        );
        return photoSpotFromServer;
    }

    private List<PhotoSpot> assertPhotoSpotsSize(int expectedSize) {
        List<PhotoSpot> photoSpots = getPhotoSpotsAsResponse().body().as(List.class); //TODO: avoid warning
        assertEquals("Wrong number of entities", expectedSize, photoSpots.size());
        return photoSpots;
    }

    private Response getPhotoSpotsAsResponse() {
        Response response = newRequest().get().thenReturn();
        response.then().assertThat().statusCode(SC_OK).contentType(JSON);
        return response;
    }

    //TODO: move to base?
    public static Long retrieveIdFromLocationHeader(Response response) {
        String location = response.header(LOCATION);
        String id = location.substring(location.lastIndexOf('/') + 1);
        return Long.valueOf(id);
    }

    public static void assertBody(Response response, PhotoSpot photoSpot) {
        assertBody(response.then(), photoSpot);
    }

    public static void assertBody(ValidatableResponse response, PhotoSpot photoSpot) {
       assertBodyById(response, photoSpot, null);
    }

    //TODO: not elegant
    //TODO: schema validation?
    public static void assertBodyById(ValidatableResponse response, PhotoSpot photoSpot, Long id) {
        response.assertThat().body(
                prefix("name", id), equalTo(photoSpot.getName()),
                prefix("description", id), equalTo(photoSpot.getDescription()),
                prefix("location.latitude", id), equalTo(photoSpot.getLocation().getLatitude()),
                prefix("location.longitude", id), equalTo(photoSpot.getLocation().getLongitude())
        );
    }

    private static String prefix(String fieldName, Long id) {
        return id == null ? fieldName : "[" + id + "]." + fieldName;
    }
}
