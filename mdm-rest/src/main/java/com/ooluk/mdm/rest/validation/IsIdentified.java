package com.ooluk.mdm.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.ooluk.mdm.rest.dto.MetaObjectData;

/**
 * A custom constraint to validate {@link MetaObjectData} is identified.
 * 
 * @author Siddhesh Prabhu
 * @since 1.0
 *
 */
@Target ( { ElementType.FIELD, ElementType.PARAMETER } )
@Retention ( RetentionPolicy.RUNTIME )
@Constraint ( validatedBy = IsIdentifiedValidator.class )
@Documented
public @interface IsIdentified {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}