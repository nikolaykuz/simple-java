package ee.devclub.model.validation;

import ee.devclub.model.Location;
import ee.devclub.model.PhotoSpot;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

//TODO: test all fields, simple unit tests
public class PhotoSpotValidationTest extends BaseValidationTest{
    private static final Location LOCATION = new Location(1, 1);

    @Test
    public void testNameTooShort() throws Exception {
        PhotoSpot photoSpot = new PhotoSpot("1", "desc", LOCATION);
        assertViolations(photoSpot, 1);
    }

    @Test
    public void testNameTooLong() throws Exception {
        String longName = RandomStringUtils.random(100 + 1);
        PhotoSpot photoSpot = new PhotoSpot(longName, "desc", LOCATION);
        assertViolations(photoSpot, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testNameNull() throws Exception {
        new PhotoSpot(null, "desc", LOCATION);
    }

    //TODO: with location


}
