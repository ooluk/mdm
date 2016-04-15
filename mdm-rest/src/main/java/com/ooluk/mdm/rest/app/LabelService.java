package com.ooluk.mdm.rest.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.ooluk.mdm.core.meta.MetaObjectType;
import com.ooluk.mdm.core.meta.app.Label;
import com.ooluk.mdm.core.meta.app.LabelRepository;
import com.ooluk.mdm.core.meta.app.LabelTypeRepository;
import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;
import com.ooluk.mdm.rest.commons.RestService;
import com.ooluk.mdm.rest.dto.LabelCore;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@Path("label")
@Produces("application/json")
public class LabelService extends RestService {

	@Autowired
	private LabelRepository lblRepository;
	
	@Autowired
	private LabelTypeRepository typeRepository;
	
	@GET
	@Path("{id}")
	public LabelCore getLabelById(@PathParam("id") Long id) throws MetaObjectNotFoundException {
		
		Label label = lblRepository.findById(id);
		if (label == null) {
			notFound(MetaObjectType.LABEL, id);
		}
		return mapper.map(label, LabelCore.class);		
	}
}
