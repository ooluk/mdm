package com.ooluk.mdm.rest.validation;

/**
 * An interface that allows validation. If you want to use {@link DataValidator} for validation your
 * implementation of this interface must register itself with the {@link ValidatorRegistry}. This
 * interface by itself does not place any such restrictions.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 * @param <T>
 */
public interface Validator<T extends Validatable<T>> {

	/**
	 * Validates the argument data. This method returns void if validation is successful and throws a
	 * ValidationFailedException if validation fails.
	 * 
	 * @param data
	 *            object to be validated
	 * 
	 * @throws ValidationFailedException
	 *             if the validation fails
	 */
	public void validate(T data);
}