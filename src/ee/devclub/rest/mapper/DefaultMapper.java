package ee.devclub.rest.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//@Provider
public class DefaultMapper implements ExceptionMapper<Exception> {
    private static final Logger log = LoggerFactory.getLogger(DefaultMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        log.error("Unexpected exception: ", exception);
        return Response.status(Response.Status.NOT_FOUND).
                build();
    }
}
