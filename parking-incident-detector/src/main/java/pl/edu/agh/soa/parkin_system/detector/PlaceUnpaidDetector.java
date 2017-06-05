package pl.edu.agh.soa.parkin_system.detector;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@MessageDriven(activationConfig = {@ActivationConfigProperty(
        propertyName = "destination",
        propertyValue = "java:/jms/queue/TicketQueue"
),
        @ActivationConfigProperty(
                propertyName = "messageSelector",
                propertyValue = "placeId is not null"
        )})
public class PlaceUnpaidDetector implements MessageListener {

    @Inject
    private ParkingDAO parkingDAO;

    @Override
    public void onMessage(Message message) {
        try {
            long placeId = message.getLongProperty("placeId");
            System.out.println("Received place: " + placeId);
            Optional<Place> place = parkingDAO.findOnePlaceById(placeId);
            boolean isValid = place.map(p -> !p.isBusy() ||
                    p.getTickets().stream()
                            .anyMatch(Ticket::isValid)
            ).orElse(false);

            if (!isValid) {
                List<ParkingUser> parkingUser = place.map(p -> parkingDAO.findUsersByArea(
                        p.getParkingMeter().getArea().getAreaId()
                )).orElse(new ArrayList<>());
                parkingUser.forEach(u -> System.out.println(
                        "Send message about place '" + placeId + "' to employee '" + u.getUsername() + "'"));
                parkingDAO.addIncident(placeId, parkingUser.stream().findAny().map(ParkingUser::getId).orElse(0L));
            }
        } catch (JMSException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
