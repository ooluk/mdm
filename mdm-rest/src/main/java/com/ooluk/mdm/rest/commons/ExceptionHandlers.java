package com.ooluk.mdm.rest.commons;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
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
	@ExceptionHandler(MetaObjectExistsException.class)
    public ResponseEntity<String> customHandler(MetaObjectExistsException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
	
	// ValidationFailed ==> 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> customHandler(MethodArgumentNotValidException ex) {
		List<String> result = new ArrayList<>();
		BindingResult binding = ex.getBindingResult();
		for (FieldError err : binding.getFieldErrors()) {
			result.add(err.getDefaultMessage());
		}
	    return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}
	
	// BadRequestException ==> 400
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> customHandler(BadRequestException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}