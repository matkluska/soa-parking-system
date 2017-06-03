package pl.edu.agh.soa.parking_system.soap;

import pl.edu.agh.soa.contracts.AreaDTO;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@WebService
public interface ParkingWebService {
    @WebMethod
    List<AreaDTO> getParkingAreas();

    @WebMethod
    AreaDTO getParkingArea(long areaId);
}
