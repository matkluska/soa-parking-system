package pl.edu.agh.soa.parkingdashboard;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class DashboardMB {
    private Set<PlaceMB> places;

    @Inject
    private ParkingDAO parkingDAO;

    @PostConstruct
    public void init() {
        places = parkingDAO.findAllPlaces().stream()
                .map(this::toPlaceMB)
                .collect(Collectors.toSet());
    }

    public Set<PlaceMB> getPlaces() {
        return places;
    }

    public void setPlaces(Set<PlaceMB> places) {
        this.places = places;
    }

    private PlaceMB toPlaceMB(Place place) {
        boolean isPaid = place.getTickets().stream()
                .anyMatch(Ticket::isValid);
        return new PlaceMB(place.getPlaceId(), place.isBusy(), isPaid);
    }
}
