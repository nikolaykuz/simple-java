package ee.devclub.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author nkuznetsov
 * @since 19.03.2015
 */
//@Provider
public class DefaultMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.NOT_FOUND).
                entity(exception.getMessage()).
                build();
    }
}
