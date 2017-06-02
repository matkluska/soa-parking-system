package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.Place;

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
}
