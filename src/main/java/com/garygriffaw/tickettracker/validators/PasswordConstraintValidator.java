package com.garygriffaw.tickettracker.validators;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordValid, Object> {

    @Override
    public void initialize(PasswordValid arg0) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        List<Rule> rules = new ArrayList<>();
        // Password length must be 8 - 20 characters
        rules.add(new LengthRule(8, 20));
        // No whitespace allowed
        rules.add(new WhitespaceRule());
        // At least one Upper-case character
        rules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
        // At least one Lower-case character
        rules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        // At least one digit
        rules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
        // At least one special character
        rules.add(new CharacterRule(EnglishCharacterData.Special, 1));

        PasswordValidator validator = new PasswordValidator(rules);
        PasswordData passwordData = new PasswordData((String) obj);
        RuleResult result = validator.validate(passwordData);

        if(!result.isValid()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Password must be 8 - 20 characters long, \n" +
                    "contain at least one uppercase letter, \n" +
                    "contain at least one lowercase letter, \n" +
                    "contain at least one digit, \n" +
                    "contain at least one special character, \n" +
                    "and not contain spaces.")
                    .addConstraintViolation();
        }

        return result.isValid();
    }
}
