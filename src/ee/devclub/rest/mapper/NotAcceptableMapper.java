package ee.devclub.rest.mapper;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;

@Provider
public class NotAcceptableMapper implements ExceptionMapper<NotAcceptableException> {
    @Override
    public Response toResponse(NotAcceptableException exception) {
        return Response.status(NOT_ACCEPTABLE).build();
    }
}
