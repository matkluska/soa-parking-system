package pl.edu.agh.soa.mock.parkingmeter;

import pl.edu.agh.soa.contracts.TicketDTO;

/**
 * @author Mateusz Kluska
 */
public interface ParkingMeterController {
    void sendTicket(TicketDTO ticket);
}
