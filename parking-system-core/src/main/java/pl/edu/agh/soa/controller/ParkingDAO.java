package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.Incident;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
public interface ParkingDAO {
    void addTicket(TicketDTO ticketDTO);

    void togglePlace(long placeId);

    List<Place> findAllPlaces();

    Optional<Place> findOnePlaceById(long placeId);

    void invalidateTicket(long ticketId);

    List<ParkingUser> findUsersByArea(long areaId);

    void addIncident(long placeId, long userId);

    List<Incident> findIncidentsAfter(long timeInMillis);

    List<Ticket> findTicketsAfter(long timeInMillis);

    List<ParkingUser> findUsersWithIncidentsAfter(long timeInMillis);

    List<Place> findAllPlacesWithTicketsAfter(long timeInMillis);

    List<Place> findAllPlacesWithIncidentsAfter(long timeInMillis);

    List<Long> getPlaceIds();

    List<Long> getAreaIds();

    Optional<ParkingUser> findUserByName(String username);

    void changeUserPassword(long userId, String password);
}
