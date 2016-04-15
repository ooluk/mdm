package com.ooluk.mdm.rest.app;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

import com.ooluk.mdm.rest.commons.MetaObjectNotFoundException;

/**
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
public class LabelLinkSupport {

	public static URI buildSelfLink(Long id) throws MetaObjectNotFoundException {
		return linkTo(methodOn(LabelService.class).getLabelById(id)).toUri();
	}
	
	public static URI buildChildrenLink(Long id) throws MetaObjectNotFoundException {
		return linkTo(methodOn(LabelService.class).getChildLabels(id)).toUri();
	}
	
	public static URI buildParentsLink(Long id) throws MetaObjectNotFoundException {
		return linkTo(methodOn(LabelService.class).getParentLabels(id)).toUri();
	}
}
