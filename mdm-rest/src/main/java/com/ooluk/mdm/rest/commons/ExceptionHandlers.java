package com.ooluk.mdm.rest.commons;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ooluk.mdm.rest.validation.ValidationFailedException;

/**
 * Exception handler for all controller thrown exceptions.
 * 
 * @author Siddhesh Prabhu
 * @since  1.0
 *
 */
@ControllerAdvice
public class ExceptionHandlers {
	
	// MetaObjectNotFound ==> 404
	@ExceptionHandler(MetaObjectNotFoundException.class)
    public ResponseEntity<String> customHandler(MetaObjectNotFoundException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	// DuplicateKey ==> 409
	@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> customHandler(DuplicateKeyException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	// ValidationFailed ==> 400
	@ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<List<String>> customHandler(ValidationFailedException ex) {
	    return new ResponseEntity<>(ex.getValidationResponse().getFailureReasons(), HttpStatus.BAD_REQUEST);
	}
}