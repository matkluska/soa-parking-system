package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.ParkingMeter;
import pl.edu.agh.soa.model.Place;
import pl.edu.agh.soa.model.Ticket;

import javax.ejb.Stateless;
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

    @Override
    public List<Place> findAllPlaces() {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Place> cq = cb.createQuery(Place.class);
//        Root<Place> rootEntry = cq.from(Place.class);
//        cq.select(rootEntry);
//        CriteriaQuery<Place> all = cq.orderBy(cb.asc(rootEntry.get("placeId")));
//        TypedQuery<Place> allQuery = em.createQuery(all);
//        return allQuery.getResultList();
        return em.createNamedQuery("Place.findAll")
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("placeWithTickets"))
                .getResultList();
    }

    @Override
    public Optional<Place> findOnePlaceById(long placeId) {
        return Optional.ofNullable(em.find(Place.class, placeId));
    }
}
