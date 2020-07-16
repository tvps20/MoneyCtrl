package com.santiago.moneyctrl.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface EmailUnique {

	String message() default "{validation.erro.model.unique}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
