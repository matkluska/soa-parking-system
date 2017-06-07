package pl.edu.agh.soa.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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
        ticket.setStartTimeInMillis(ticketDTO.getStartTimeInMinutes() * 60 * 1000);
        ticket.setDurationInMillis(ticketDTO.getDurationInMinutes() * 60 * 1000);
        ticket.setValid(true);
        parkingMeter.getTickets().add(ticket);
        em.persist(ticket);

        messagePublisher.publishTicket(ticket.getTicketId(), ticket.getDurationInMillis(), place.getPlaceId());
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
    public List<Place> findAllPlacesFromArea(long areaId) {
        return em.createNamedQuery("Place.findAllFromArea", Place.class)
                .setParameter("area", areaId)
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
    public List<ParkingUser> findAllUsers() {
        return em.createNamedQuery("User.findAllUser", ParkingUser.class)
                .getResultList();
    }

    @Override
    public List<ParkingUser> findUsersByArea(long areaId) {
        Area area = em.getReference(Area.class, areaId);
        return em.createNamedQuery("User.findByArea", ParkingUser.class)
                .setParameter("area", area)
                .getResultList();
    }

    @Override
    public void addIncident(long placeId, long userId) {
        Place place = em.getReference(Place.class, placeId);
        ParkingUser user = em.getReference(ParkingUser.class, userId);
        Incident incident = new Incident();
        incident.setPlace(place);
        incident.setParkingUser(user);
        incident.setTimeInMillis(System.currentTimeMillis());
        em.persist(incident);
    }

    @Override
    public List<Incident> findIncidentsAfter(long timeInMillis) {
        return em.createNamedQuery("Incident.findAfter", Incident.class)
                .setParameter("time", timeInMillis)
                .getResultList();
    }

    @Override
    public Optional<Incident> findIncidentByPlace(Place place) {
        return em.createNamedQuery("Incident.findByPlace", Incident.class)
                .setParameter("place", place)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<Ticket> findTicketsAfter(long timeInMillis) {
        return em.createNamedQuery("Ticket.findAfter", Ticket.class)
                .setParameter("time", timeInMillis)
                .getResultList();
    }

    @Override
    public List<ParkingUser> findUsersWithIncidentsAfter(long timeInMillis) {
        return em.createNamedQuery("User.findWithIncidentsBefore", ParkingUser.class)
                .setParameter("time", timeInMillis)
                .getResultList();
    }

    @Override
    public List<Place> findAllPlacesWithTicketsAfter(long timeInMillis) {
        return em.createNamedQuery("Place.findAllWithTicketsAfter", Place.class)
                .setParameter("time", timeInMillis)
                .getResultList();
    }

    @Override
    public List<Place> findAllPlacesWithIncidentsAfter(long timeInMillis) {
        return em.createNamedQuery("Place.findAllWithIncidentsAfter", Place.class)
                .setParameter("time", timeInMillis)
                .getResultList();
    }

    @Override
    public List<Long> getPlaceIds() {
        return em.createNamedQuery("Place.getPlaceIds", Long.class)
                .getResultList();
    }

    @Override
    public List<Long> getAreaIds() {
        return em.createNamedQuery("Area.getAreaIds", Long.class)
                .getResultList();
    }

    @Override
    public Optional<ParkingUser> findUserByName(String username) {
        return Optional.ofNullable(em.createNamedQuery("User.findByName", ParkingUser.class)
                .setParameter("name", username)
                .getSingleResult());
    }

    @Override
    public void changeUserPassword(long userId, String password) {
        ParkingUser user = em.find(ParkingUser.class, userId);
        byte[] result = DigestUtils.sha256(password);
        String encPass = Base64.encodeBase64String(result);
        user.setPassword(encPass);
        em.merge(user);
    }
}
