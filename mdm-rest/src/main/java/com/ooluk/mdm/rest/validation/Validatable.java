package com.ooluk.mdm.rest.validation;

/**
 * An interface that allows an object to be validated.  
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@FunctionalInterface
public interface Validatable<T> {

	ValidationResponse validate(T data);
}
