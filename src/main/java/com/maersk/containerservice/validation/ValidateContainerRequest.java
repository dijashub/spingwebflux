package com.maersk.containerservice.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ContainerRequestValidator.class)
@Documented
public @interface ValidateContainerRequest {

    String message() default "";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};


}

