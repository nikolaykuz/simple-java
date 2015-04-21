package ee.devclub.model.validation;

import ee.devclub.model.Location;
import org.junit.Test;

//TODO:
public class LocationValidationTest extends BaseValidationTest {

    @Test
    public void testName() throws Exception {
        Location location = new Location(100f, 100f);
        assertViolations(location, 1);
    }

    @Test
    public void testName2() throws Exception {
        Location location = new Location(89.123f, 90f);
        assertViolations(location, 1);
    }
}
