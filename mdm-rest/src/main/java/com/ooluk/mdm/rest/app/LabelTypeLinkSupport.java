package com.ooluk.mdm.rest.app;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;

/**
 * Static methods to build links for label types.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelTypeLinkSupport {

	private static Logger LOGGER = LogManager.getLogger(); 
	
	public static URI buildSelfLink(Long id) {
		URI uri = null;		
		try {
			uri = linkTo(methodOn(LabelTypeRestService.class).getTypeById(id)).toUri();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
		return uri;
	}
	
	public static URI buildLabelsLink(Long id) throws MetaObjectNotFoundException {
		URI uri = null;		
		try {
			uri = linkTo(methodOn(LabelRestService.class).getLabels(id)).toUri();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
		return uri;
	}
}
