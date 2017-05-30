package pl.edu.agh.soa.parking_system.soap;

import pl.edu.agh.soa.controller.ParkingDAO;

import javax.inject.Inject;
import javax.jws.WebService;

/**
 * @author Mateusz Kluska
 */
@WebService(name = "PlaceSensorMockService", endpointInterface = "pl.edu.agh.soa.parking_system.soap.PlaceSensorMock")
public class PlaceSensorMockImpl implements PlaceSensorMock {
    @Inject
    ParkingDAO parkingDAO;

    @Override
    public void togglePLace(long placeId) {
        parkingDAO.togglePlace(placeId);
    }
}
