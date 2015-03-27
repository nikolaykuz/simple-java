package ee.devclub.restassured;

import com.jayway.restassured.specification.RequestSpecification;
import ee.devclub.rest.PhotoSpotResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response.Status;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class BaseIntegrationTest extends JerseyTest {
    private final static Logger log = LoggerFactory.getLogger(BaseIntegrationTest.class);
    private final static String SLASH = "/";
    public static final String LOCATION = "Location";
    public static final double EPSILON = 0.00000001;
    public final static int OK = Status.OK.getStatusCode();
    public final static int CREATED = Status.CREATED.getStatusCode();

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
