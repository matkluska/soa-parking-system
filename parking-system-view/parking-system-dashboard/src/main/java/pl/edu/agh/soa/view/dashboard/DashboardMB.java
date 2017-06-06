package pl.edu.agh.soa.view.dashboard;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;
import pl.edu.agh.soa.model.UserType;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;
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
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        ParkingUser user = parkingDAO.findUserByName(username).orElseThrow(RuntimeException::new);
        List<Place> userPlaces;
        if (UserType.ADMIN.equals(user.getUserType())) {
            userPlaces = parkingDAO.findAllPlaces();
        } else {
            userPlaces = parkingDAO.findAllPlacesFromArea(user.getArea().getAreaId());
        }
        places = userPlaces.stream()
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
