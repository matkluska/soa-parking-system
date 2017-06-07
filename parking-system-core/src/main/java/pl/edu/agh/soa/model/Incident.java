package pl.edu.agh.soa.model;

import javax.persistence.*;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
@NamedQueries({
        @NamedQuery(name = "Incident.findAfter",
                query = "select i from Incident i where i.timeInMillis > :time"),
        @NamedQuery(name = "Incident.findByPlace",
                query = "select i from Incident i where i.place=:place order by i.timeInMillis desc")
})
public class Incident {
    private long id;
    private Place place;
    private ParkingUser parkingUser;
    private long timeInMillis;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID", nullable = false)
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_USER_ID", nullable = false)
    public ParkingUser getParkingUser() {
        return parkingUser;
    }

    public void setParkingUser(ParkingUser parkingUser) {
        this.parkingUser = parkingUser;
    }

    @Column(name = "TIME_IN_MILLIS")
    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
