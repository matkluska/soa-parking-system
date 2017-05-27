package pl.edu.agh.soa.controller;

import pl.edu.agh.soa.contracts.TicketDTO;
import pl.edu.agh.soa.model.Area;
import pl.edu.agh.soa.model.ParkingMeter;
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
public class ParkingDAO {
    @PersistenceContext
    private EntityManager em;

    public void addTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setCarId(ticketDTO.getCarId());
        ticket.setStartTimeInMinutes(ticketDTO.getStartTimeInMinutes());
        ticket.setDurationInMinutes(ticketDTO.getDurationInMinutes());
        Area area = new Area();
        ParkingMeter parkingMeter = new ParkingMeter();
        parkingMeter.setArea(area);
        ticket.setParkingMeter(parkingMeter);
        em.persist(area);
        em.persist(parkingMeter);
        em.persist(ticket);
    }
}
