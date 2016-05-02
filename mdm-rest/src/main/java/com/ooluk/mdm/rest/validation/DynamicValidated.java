package com.ooluk.mdm.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ooluk.mdm.data.meta.DynamicProperties;

/**
 * A custom constraint to validate {@link DynamicProperties}.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Target ( { ElementType.FIELD, ElementType.PARAMETER } )
@Retention ( RetentionPolicy.RUNTIME )
@Constraint ( validatedBy = DynamicPropertiesValidator.class )
@Documented
public @interface DynamicValidated {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}