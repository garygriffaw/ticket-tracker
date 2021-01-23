package com.garygriffaw.tickettracker.validators;

import com.garygriffaw.tickettracker.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesUpdateValidator implements ConstraintValidator<PasswordMatchesUpdate, Object> {

    @Override
    public void initialize(PasswordMatchesUpdate constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        PasswordDto user = (PasswordDto) obj;

        boolean isValid = user.getPassword().equals(user.getConfirmPassword());
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode( "confirmPassword" ).addConstraintViolation();
        }

        return isValid;
    }
}
