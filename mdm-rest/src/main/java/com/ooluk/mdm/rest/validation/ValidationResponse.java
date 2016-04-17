package com.ooluk.mdm.rest.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * A response of a validation action consisting of the validation status and failure messages if
 * any. The validation success is internally determined by absence of failure messages. Do not add
 * any failure messages if you want to convey a successful validation.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
public class ValidationResponse {

	private List<String> failureReasons = new ArrayList<>();
	
	/**
	 * Tells when the validation was successful.
	 * 
	 * @return true if validation was successful, false otherwise.
	 */
	public boolean isValid() {
		return failureReasons.isEmpty();
	}
	
	/**
	 * Returns a list of validation failure reasons. An empty list is returned if no validation
	 * failures occurred.
	 * 
	 * @return list of failures reasons
	 */
	public List<String> getFailureReasons() {
		return failureReasons;
	}
	
	/**
	 * Adds a validation failure reason to the list of failure reasons.
	 * 
	 * @param reason
	 *            validation failure reason
	 *            
	 * @return a reference to this to provide a fluent interface.
	 */
	public ValidationResponse addReason(String reason) {
		failureReasons.add(reason);
		return this;
	}
}