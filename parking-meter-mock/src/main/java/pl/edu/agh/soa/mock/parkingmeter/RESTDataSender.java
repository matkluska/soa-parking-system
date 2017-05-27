package pl.edu.agh.soa.mock.parkingmeter;

import pl.edu.agh.soa.contracts.TicketDTO;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 * @author Mateusz Kluska
 */
@Stateless
public class RESTDataSender implements DataSender {

    private static final String PARKING_SYSTEM_URI = "http://localhost:8080/parkingsystem";
    private static final String TICKET_PATH = "ticket";
    private Client client = ClientBuilder.newClient();

    @Override
    public void sendTicket(TicketDTO ticket) {
        client.target(PARKING_SYSTEM_URI)
                .path(TICKET_PATH)
                .request(MediaType.APPLICATION_JSON)
//                .get();
                .post(Entity.json(ticket));

    }
}
