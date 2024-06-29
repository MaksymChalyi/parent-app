package com.maksimkaxxl.springbootrestfulapi.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
@NotNull(message = "Position must not be null")
@Pattern(regexp = "\\D+", message = "Position must not contain numbers")
public @interface NotNumericPosition {
    String message() default "Invalid position";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
