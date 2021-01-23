package com.garygriffaw.tickettracker.validators;

import com.garygriffaw.tickettracker.dto.RegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesRegisterValidator
        implements ConstraintValidator<PasswordMatchesRegister, Object> {

    @Override
    public void initialize(PasswordMatchesRegister constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegisterDto user = (RegisterDto) obj;

        boolean isValid = user.getPassword().equals(user.getConfirmPassword());
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode( "confirmPassword" ).addConstraintViolation();
        }

        return isValid;
    }
}
