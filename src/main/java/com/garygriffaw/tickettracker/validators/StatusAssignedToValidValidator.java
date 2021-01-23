package com.garygriffaw.tickettracker.validators;

import com.garygriffaw.tickettracker.dto.TicketUpdateDto;
import com.garygriffaw.tickettracker.enums.TicketStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusAssignedToValidValidator implements ConstraintValidator<StatusAssignedToValid, Object> {

    @Override
    public void initialize(StatusAssignedToValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        TicketUpdateDto ticket = (TicketUpdateDto) obj;

        boolean isValid = false;
        String errorMessage = null;

        String assignedToUserName = ticket.getAssignedToUserName();
        TicketStatus ticketStatus = TicketStatus.valueOf(ticket.getTicketStatus());
        String ticketStatusDisplayValue = TicketStatus.valueOf(ticket.getTicketStatus()).getDisplayValue();
        String closureComment = ticket.getClosureComment();

        switch(ticketStatus) {
            case UNASSIGNED:
                errorMessage = validateStatusUnassigned(ticketStatusDisplayValue, assignedToUserName);
                break;
            case COMPLETE:
                errorMessage = validateStatusComplete(ticketStatusDisplayValue, assignedToUserName, closureComment);
                break;
            case CANCELLED:
                errorMessage = validateStatusCancelled(ticketStatusDisplayValue, closureComment);
                break;
            default:
                errorMessage = validateStatusOther(ticketStatusDisplayValue, assignedToUserName);
        }

        if(errorMessage == null)
            isValid = true;
        else
            isValid = false;

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addPropertyNode( "assignedToUserName" )
                    .addConstraintViolation();
        }

        return isValid;
    }

    private String validateStatusUnassigned(String ticketStatusDisplayValue, String assignedToUserName) {
        String errorMessage = null;

        if(!assignedToUserName.isEmpty() &&
                assignedToUserName != null)
            errorMessage = "Assigned To must be \"Unassigned\" when Status is " + ticketStatusDisplayValue;

        return errorMessage;
    }

    private String validateStatusComplete(String ticketStatusDisplayValue, String assignedToUserName, String closedComment) {
        String errorMessage = null;

        if(assignedToUserName.isEmpty() ||
                assignedToUserName == null)
            errorMessage = "Assigned To must have a user when Status is " + ticketStatusDisplayValue;
        else if(closedComment.isEmpty())
            errorMessage = "Closure Comment must be provided when Status is " + ticketStatusDisplayValue;

        return errorMessage;
    }

    private String validateStatusCancelled(String ticketStatusDisplayValue, String closureComment) {
        String errorMessage = null;

        if(closureComment.isEmpty())
            errorMessage = "Closure Comment must be provided when Status is " + ticketStatusDisplayValue;

        return errorMessage;
    }

    private String validateStatusOther(String ticketStatusDisplayValue, String assignedToUserName) {
        String errorMessage = null;

        if(assignedToUserName.isEmpty() ||
                assignedToUserName == null)
            errorMessage = "Assigned To must have a user when Status is " + ticketStatusDisplayValue;

        return errorMessage;
    }
}
