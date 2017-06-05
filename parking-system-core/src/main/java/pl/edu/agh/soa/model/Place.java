package pl.edu.agh.soa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
@NamedQueries({
        @NamedQuery(name = "Place.findAll", query = "SELECT p from Place p"),
        @NamedQuery(name = "Place.findAllWithIncidentsAfter", query = "SELECT p from Place p join fetch p.incidents i where i.timeInMillis > :time"),
        @NamedQuery(name = "Place.findAllWithTicketsAfter", query = "SELECT p from Place p join fetch p.tickets t where t.startTimeInMillis > :time"),
        @NamedQuery(name = "Place.getPlaceIds", query = "select p.placeId from Place p")
})
@NamedEntityGraph(name = "placeWithTickets",
        attributeNodes = @NamedAttributeNode(value = "tickets"))
public class Place {
    private long placeId;
    private boolean isBusy;
    private ParkingMeter parkingMeter;
    private Set<Ticket> tickets = new HashSet<>();
    private Set<Incident> incidents = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID")
    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    @Column(name = "IS_BUSY")
    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_METER_ID", nullable = false)
    public ParkingMeter getParkingMeter() {
        return parkingMeter;
    }

    public void setParkingMeter(ParkingMeter parkingMeter) {
        this.parkingMeter = parkingMeter;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "place")
    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }
}
