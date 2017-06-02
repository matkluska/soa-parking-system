package pl.edu.agh.soa.parkingdashboard;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.Place;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class DashboardMB {
    private List<PlaceMB> places;

    @Inject
    private ParkingDAO parkingDAO;

    @PostConstruct
    public void init() {
        places = parkingDAO.findAllPlaces().stream()
                .map(this::toPlaceMB)
                .collect(Collectors.toList());
    }

    public List<PlaceMB> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceMB> places) {
        this.places = places;
    }

    private PlaceMB toPlaceMB(Place place) {
        boolean isPaid = Objects.nonNull(place.getTicket());
        return new PlaceMB(place.getPlaceId(), place.isBusy(), isPaid);
    }
}
