package pl.edu.agh.soa.parkingsensors.mock;

import pl.edu.agh.soa.parking_system.soap.PlaceSensorMock;
import pl.edu.agh.soa.parking_system.soap.PlaceSensorMockImplService;

import javax.ejb.Stateless;

/**
 * @author Mateusz Kluska
 */
@Stateless
public class SOAPPlaceSensorController implements PlaceSensorController {
    private final PlaceSensorMock placeSensorMock;

    public SOAPPlaceSensorController() {
        PlaceSensorMockImplService placeSensorMockImplService = new PlaceSensorMockImplService();
        placeSensorMock = placeSensorMockImplService.getPlaceSensorMockServicePort();
    }

    @Override
    public void toggle(long placeId) {
        placeSensorMock.togglePLace(placeId);
    }
}
