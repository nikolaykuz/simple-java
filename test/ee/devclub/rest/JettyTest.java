package ee.devclub.rest;

import ee.devclub.model.PhotoSpot;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * @author nkuznetsov
 * @since 11.03.2015
 */
public class JettyTest {
    private Server server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = new Server(8080);
        server.setStopAtShutdown(true);
        WebAppContext context = new WebAppContext("webapp", "/");
        server.setHandler(context);
        server.start();
        target = ClientBuilder.newClient().target(server.getURI());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        String responseMsg = target.path("rest/photo-spots/ids/0").request().get(String.class);
        PhotoSpot photoSpot = target.path("rest/photo-spots/ids/0").request().get(PhotoSpot.class);
        assertEquals("Wrong name", "Kohtuotsa vaateplatvorm", photoSpot.getName());
        assertEquals("Photospots do not match", serialize(photoSpot), responseMsg);

    }


    //TODO: jsonpath
    private String serialize(Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        marshaller.marshal(object, stringWriter);
        return stringWriter.toString();
    }
}
