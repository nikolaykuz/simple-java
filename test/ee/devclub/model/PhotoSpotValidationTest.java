package ee.devclub.model;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class PhotoSpotValidationTest {
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testName() throws Exception {
        PhotoSpot photoSpot = new PhotoSpot("1", "desc", new Location(1, 1));
        Set<ConstraintViolation<PhotoSpot>> violations = validator.validate(photoSpot);
        assertEquals(1, violations.size());
    }
}
