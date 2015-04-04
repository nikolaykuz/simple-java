package ee.devclub.rest.mapper;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

@Provider
public class NotAllowedMapper implements ExceptionMapper<NotAllowedException> {
    @Override
    public Response toResponse(NotAllowedException exception) {
        return Response.status(METHOD_NOT_ALLOWED).build();
    }
}
