package ee.devclub.model.validation;

import org.junit.Before;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

public class BaseValidationTest {
    protected static Validator validator;

    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected <E> void assertViolations(E entity, int violationSize) {
        assertEquals(violationSize, validator.validate(entity).size());
    }
}
