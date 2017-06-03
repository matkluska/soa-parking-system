package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author Mateusz Kluska
 */
@Stateless
@Transactional
public class DBParkingDAO implements ParkingDAO {

    @PersistenceContext
    private EntityManager em;

    private final MessagePublisher messagePublisher;

    @Inject
    public DBParkingDAO(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void addTicket(TicketDTO ticketDTO) {
        ParkingMeter parkingMeter = em.getReference(ParkingMeter.class, ticketDTO.getParkingMeterId());
        Place place = em.getReference(Place.class, ticketDTO.getPlaceId());
        Ticket ticket = new Ticket();
        ticket.setCarId(ticketDTO.getCarId());
        ticket.setParkingMeter(parkingMeter);
        ticket.setPlace(place);
        ticket.setStartTimeInMinutes(ticketDTO.getStartTimeInMinutes());
        ticket.setDurationInMinutes(ticketDTO.getDurationInMinutes());
        ticket.setValid(true);
        parkingMeter.getTickets().add(ticket);
        em.persist(ticket);

        messagePublisher.publishTicket(ticket.getTicketId(), ticket.getDurationInMinutes(), place.getPlaceId());
    }

    @Override
    public void togglePlace(long placeId) {
        Place place = em.find(Place.class, placeId);
        boolean currentState = place.isBusy();
        place.setBusy(!currentState);
        em.merge(place);

        if (place.isBusy()) {
            messagePublisher.publishPlace(placeId);
        }
    }

    @Override
    public List<Place> findAllPlaces() {
        return em.createNamedQuery("Place.findAll", Place.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("placeWithTickets"))
                .getResultList();
    }

    @Override
    public Optional<Place> findOnePlaceById(long placeId) {
        return Optional.ofNullable(em.find(Place.class, placeId));
    }

    @Override
    public void invalidateTicket(long ticketId) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        ticket.setValid(false);
        em.merge(ticket);
    }

    @Override
    public List<ParkingUser> findUsersByArea(long areaId) {
        Area area = em.getReference(Area.class, areaId);
        return em.createNamedQuery("User.findByArea", ParkingUser.class)
                .setParameter("area", area)
                .getResultList();
    }

}
