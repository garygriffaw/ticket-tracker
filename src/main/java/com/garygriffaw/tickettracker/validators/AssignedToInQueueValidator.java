package com.garygriffaw.tickettracker.validators;

import com.garygriffaw.tickettracker.dto.TicketUpdateDto;
import com.garygriffaw.tickettracker.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AssignedToInQueueValidator implements ConstraintValidator<AssignedToInQueue, Object> {

    @Autowired
    UserAccountService userAccountService;

    @Override
    public void initialize(AssignedToInQueue constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        TicketUpdateDto ticket = (TicketUpdateDto) obj;

        boolean isValid;
        String errorMessage = null;
        String assignedTo = ticket.getAssignedToUserName();

        if(assignedTo.isEmpty() || assignedTo == null)
            isValid = true;
        else {
            int count = userAccountService.getCountUserAccountQueue(assignedTo, ticket.getQueueId());
            if (count == 0) {
                isValid = false;
                errorMessage = "Assigned To is not in the selected Queue.";
            } else
                isValid = true;
        }

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode( "assignedToUserName" )
                    .addConstraintViolation();
        }

        return isValid;
    }
}
