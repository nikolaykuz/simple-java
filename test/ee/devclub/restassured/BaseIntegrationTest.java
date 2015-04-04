package ee.devclub.restassured;

import com.jayway.restassured.specification.RequestSpecification;
import ee.devclub.rest.PhotoSpotResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Application;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


//TODO: phylosopy of test: corner cases

//TODO: make generic test?

//TODO: hardcoded values to parameters

//TODO: DBUnit?

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class BaseIntegrationTest extends JerseyTest {
    private final static String SLASH = "/";

    protected final String basePath;

    public BaseIntegrationTest(String basePath) {
        if (basePath.endsWith(SLASH)) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        this.basePath = basePath;
    }

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(PhotoSpotResource.class)
                .property("contextConfigLocation", "classpath:spring-test.xml")
                .packages("ee.devclub.rest");
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new JettyTestContainerFactory();
    }

    public RequestSpecification newRequest() {
        return given()
                .port(getPort())
                .basePath(basePath)
                .contentType(APPLICATION_JSON);
    }

}
