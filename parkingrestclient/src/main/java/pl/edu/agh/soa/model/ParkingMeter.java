package pl.edu.agh.soa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
public class ParkingMeter {
    private long parkingMeterId;
    private Area area;
    private Set<Ticket> tickets = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARKING_METER_ID")
    public long getParkingMeterId() {
        return parkingMeterId;
    }

    public void setParkingMeterId(long parkingMeterId) {
        this.parkingMeterId = parkingMeterId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID", nullable = false)
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingMeter")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
