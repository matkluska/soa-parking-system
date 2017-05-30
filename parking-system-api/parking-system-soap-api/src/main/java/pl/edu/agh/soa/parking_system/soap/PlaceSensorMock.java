package pl.edu.agh.soa.parking_system.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author Mateusz Kluska
 */
@WebService
@SOAPBinding
public interface PlaceSensorMock {
    @WebMethod
    void togglePLace(long placeId);
}
