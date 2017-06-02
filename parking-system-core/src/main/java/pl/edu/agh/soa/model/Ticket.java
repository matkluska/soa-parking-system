package pl.edu.agh.soa.model;

import javax.persistence.*;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
public class Ticket {
    private long ticketId;
    private ParkingMeter parkingMeter;
    private Place place;
    private String carId;
    private long durationInMinutes;
    private long startTimeInMinutes;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_METER_ID", nullable = false)
    public ParkingMeter getParkingMeter() {
        return parkingMeter;
    }

    public void setParkingMeter(ParkingMeter parkingMeter) {
        this.parkingMeter = parkingMeter;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID")
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Column(name = "CAR_ID")
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Column(name = "DURATION_IN_MINUTES")
    public long getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Column(name = "START_TIME_IN_MINUTES")
    public long getStartTimeInMinutes() {
        return startTimeInMinutes;
    }

    public void setStartTimeInMinutes(long startTimeInMinutes) {
        this.startTimeInMinutes = startTimeInMinutes;
    }
}
