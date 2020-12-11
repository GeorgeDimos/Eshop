package com.spring.eshop.entity.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
	String message() default "{email.in.use}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
