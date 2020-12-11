package com.spring.eshop.entity.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {
	String message() default "{username.in.use}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
