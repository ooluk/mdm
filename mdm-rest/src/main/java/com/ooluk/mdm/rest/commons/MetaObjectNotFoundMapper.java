package com.ooluk.mdm.rest.commons;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Mapper for MetaObjectNotFoundException.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Provider
public class MetaObjectNotFoundMapper implements ExceptionMapper<MetaObjectNotFoundException>{

	@Override
	public Response toResponse(MetaObjectNotFoundException ex) {
		return Response.status(404)
				.entity(ex.getMessage())
				.type(MediaType.TEXT_PLAIN)
				.build();
	}
}