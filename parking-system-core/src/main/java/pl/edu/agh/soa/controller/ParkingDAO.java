package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;

/**
 * @author Mateusz Kluska
 */
public interface ParkingDAO {
    void addTicket(TicketDTO ticketDTO);

    void togglePlace(long placeId);
}
