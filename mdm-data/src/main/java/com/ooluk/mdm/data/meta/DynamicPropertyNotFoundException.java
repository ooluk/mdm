package com.ooluk.mdm.data.meta;

/**
 * Exception to signal that a specified dynamic property does not exist.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class DynamicPropertyNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String property;
	
	public DynamicPropertyNotFoundException(String property) {
		super();
		this.property = property;
	}
	
	DynamicPropertyNotFoundException(String property, String message) {
		super(message);
		this.property = property;
	}
	
	@Override
	public String toString() {
		return getMessage() == null ? defaultMessage() : getMessage();
	}
	
	private String defaultMessage() {
		return "Dynamic property " + property + " not found";
	}
}