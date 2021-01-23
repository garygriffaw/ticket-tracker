package com.garygriffaw.tickettracker.comparators;

import com.garygriffaw.tickettracker.entities.Ticket;

import java.util.Comparator;

public class TicketCreatedDateTimeComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket t1, Ticket t2) {
        //  Tickets should be sorted by CreatedDateTime descending

        if (t1.getCreatedDateTime().isBefore(t2.getCreatedDateTime())) return 1;
        if (t1.getCreatedDateTime().isAfter(t2.getCreatedDateTime())) return -1;
        return 0;
    }
}
