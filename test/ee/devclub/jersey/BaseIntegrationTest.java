package ee.devclub.jersey;

import ee.devclub.rest.PhotoSpotResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class BaseIntegrationTest extends JerseyTest {
    protected final String basePath;
    protected Response response;

    public BaseIntegrationTest(String basePath) {
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

    protected Response get(String subPath) {
        //TODO: handle leading and trailing /
        return target(basePath + subPath).request().get();
    }

    protected Response get() {
        return get("");
    }

    public Response getAndAssertStatus(String subPath, Response.Status status) {
        Response response = get(subPath);
        assertEquals(response.getStatusInfo().getStatusCode(), status.getStatusCode());
        return response;
    }

    public Response getAndAssertStatus(Response.Status status) {
        return getAndAssertStatus("", status);
    }

}
