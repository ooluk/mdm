package com.ooluk.mdm.rest.validation;

/**
 * A marker interface for objects that can be validated using {@link Validator}.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public interface Validatable<T extends Validatable<T>> {
}