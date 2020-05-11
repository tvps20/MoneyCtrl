package com.santiago.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.santiago.services.interfaces.IServiceValidator;

@Constraint(validatedBy = CustomUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface CustomUnique {
	
	String message() default "{validation.erro.model.unique}";
	Class<? extends IServiceValidator> classType();
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
