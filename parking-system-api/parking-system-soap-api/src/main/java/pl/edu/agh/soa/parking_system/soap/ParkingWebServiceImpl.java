package pl.edu.agh.soa.parking_system.soap;

import pl.edu.agh.soa.contracts.AreaDTO;
import pl.edu.agh.soa.parking_system.soap.service.ParkingService;

import javax.inject.Inject;
import javax.jws.WebService;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@WebService(name = "ParkingWebService", endpointInterface = "pl.edu.agh.soa.parking_system.soap.ParkingWebService")
public class ParkingWebServiceImpl implements ParkingWebService {
    @Inject
    private ParkingService parkingService;

    @Override
    public List<AreaDTO> getParkingAreas() {
        return parkingService.getAreas();
    }

    @Override
    public AreaDTO getParkingArea(long areaId) {
        return parkingService.getArea(areaId);
    }
}
