package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.ParkingMeter;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * @author Mateusz Kluska
 */
@Stateless
@Transactional
public class DBParkingDAO implements ParkingDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void addTicket(TicketDTO ticketDTO) {
        ParkingMeter parkingMeter = em.getReference(ParkingMeter.class, ticketDTO.getParkingMeterId());
        Ticket ticket = new Ticket();
        ticket.setCarId(ticketDTO.getCarId());
        ticket.setParkingMeter(parkingMeter);
        ticket.setStartTimeInMinutes(ticketDTO.getStartTimeInMinutes());
        ticket.setDurationInMinutes(ticketDTO.getDurationInMinutes());
        parkingMeter.getTickets().add(ticket);
        em.persist(ticket);
    }

    @Override
    public void togglePlace(long placeId) {
        Place place = em.find(Place.class, placeId);
        boolean currentState = place.isBusy();
        place.setBusy(!currentState);
        em.merge(place);
    }
}
