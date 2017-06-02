package pl.edu.agh.soa.model;

import javax.persistence.*;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
public class Place {
    private long placeId;
    private boolean isBusy;
    private ParkingMeter parkingMeter;
    private Ticket ticket;

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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "place", cascade = CascadeType.ALL)
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
