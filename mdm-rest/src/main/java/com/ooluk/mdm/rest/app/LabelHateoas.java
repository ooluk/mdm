package com.ooluk.mdm.rest.app;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Collection of static methods to build HATEOAS links to label resources.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelHateoas {

	private static Logger LOGGER = LogManager.getLogger(); 
	
	public static URI buildSelfLink(Long id) {
		URI uri = null;		
		try {
			uri = linkTo(methodOn(LabelRestService.class).getLabelById(id)).toUri();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
		return uri;
	}
	
	public static URI buildChildrenLink(Long id) {
		URI uri = null;		
		try {
			uri = linkTo(methodOn(LabelRestService.class).getChildLabels(id)).toUri();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
		return uri;
	}
	
	public static URI buildParentsLink(Long id) {
		URI uri = null;		
		try {
			uri = linkTo(methodOn(LabelRestService.class).getParentLabels(id)).toUri();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage(), e);
		}
		return uri;
	}
}
