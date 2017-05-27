package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Mateusz Kluska
 */
@Path("/ticket")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParkingMeterResource {

    @Inject
    ParkingDAO parkingDAO;

    @POST
    public void addTicket(TicketDTO ticket) {
        parkingDAO.addTicket(ticket);
    }
}
