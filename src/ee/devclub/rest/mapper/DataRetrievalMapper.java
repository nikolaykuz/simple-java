package ee.devclub.rest.mapper;

import org.springframework.dao.DataRetrievalFailureException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author nkuznetsov
 * @since 20.03.2015
 */
@Provider
public class DataRetrievalMapper implements ExceptionMapper<DataRetrievalFailureException>{
    @Override
    public Response toResponse(DataRetrievalFailureException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
