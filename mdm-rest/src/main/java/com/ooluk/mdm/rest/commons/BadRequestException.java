package com.ooluk.mdm.rest.commons;

/**
 * Thrown when a bad request is detected.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BadRequestException(String msg) {
		super(msg);
	}
}