package com.ooluk.mdm.rest.dto;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * A generic class that wraps the DTOs to provide HATEOAS support. This was designed because
 * Spring's HATEOAS support relies on HAL which is more verbose and more difficult to parse.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class RestResponse<T extends MetaObjectDto> {
	
	private final T content;	
	private final Map<String, URI> links;
	
	/**
	 * Constructs a RestResponse class wrapping the specified object.
	 * 
	 * @param content
	 *            the object to wrap
	 */
    public RestResponse(T content) {
        this.content = content;
        links = new HashMap<>();
    }

	/**
	 * Returns the DTO wrapped in this response.
	 * 
	 * @return the DTO wrapped
	 */
    public T getContent() {
        return content;
    }
    
    /**
     * Returns all links.
     * 
     * @return a map of link names and URIs.
     */
    public Map<String, URI> getLinks() {
    	return links;
    }
    
    /**
	 * Adds a link to the response.
	 * 
	 * @param name
	 *            link name
	 * @param link
	 *            link URI
	 *            
	 * @return a reference to this to provide a fluent interface
	 */
    public RestResponse<T> addLink(String name, URI link) {
    	links.put(name, link);
    	return this;
    }
}