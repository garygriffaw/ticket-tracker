package com.garygriffaw.tickettracker.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AssignedToInQueueValidator.class)
@Documented
public @interface AssignedToInQueue {
    String message() default "Assigned To is not in the selected Queue";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
