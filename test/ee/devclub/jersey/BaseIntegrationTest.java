package ee.devclub.jersey;

import ee.devclub.rest.PhotoSpotResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.jetty.JettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.lang.reflect.Field;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

public class BaseIntegrationTest extends JerseyTest {
    private final static Logger log = LoggerFactory.getLogger(BaseIntegrationTest.class);
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

    private Invocation.Builder constructRequest(String subPath) {
        //add leading slash if needed
        if (!subPath.startsWith(SLASH)) {
            subPath = SLASH + subPath;
        }
        return target(basePath + subPath).request();
    }

    protected Response httpGet(String subPath) {
        return constructRequest(subPath).get();
    }

    protected Response httpPost(Object entity) {
        return constructRequest("").post(Entity.entity(toQueryParams(entity), APPLICATION_FORM_URLENCODED));
    }

    protected Response httpDelete(String subPath) {
        return constructRequest(subPath).delete();
    }

    protected Response httpHead(String subPath) {
        return constructRequest(subPath).head();
    }

    protected Response assertStatus(Status status, Response response) {
        assertEquals("Wrong HTTP status received", status.getStatusCode(), response.getStatusInfo().getStatusCode());
        return response;
    }

    protected Response assertOk(Response response) {
        assertStatus(OK, response);
        return response;
    }

    protected Response assertNotFound(Response response) {
        assertStatus(NOT_FOUND, response);
        return response;
    }

    //TODO: rewrite from scratch for practice
    public static MultivaluedMap<String, String> toQueryParams(Object ob) {
        MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<String, String>();
        final Field[] fields = ob.getClass().getDeclaredFields();
        for (Field field : fields) {
            final boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                final Object value = field.get(ob);
                if (value != null) {
                    final String name = field.getName();
                    queryParams.add(name, value.toString());
                }
            } catch (IllegalAccessException e) {
                log.error("Error accessing a field", e);
            } finally {
                field.setAccessible(accessible);
            }
        }
        return queryParams;
    }

}
