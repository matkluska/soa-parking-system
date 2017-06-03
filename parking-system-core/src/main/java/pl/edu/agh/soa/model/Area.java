package pl.edu.agh.soa.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(schema = "parking")
public class Area {
    private long areaId;
    private Set<ParkingMeter> parkingMeters = new HashSet<>();
    private Set<ParkingUser> users = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AREA_ID")
    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    public Set<ParkingMeter> getParkingMeters() {
        return parkingMeters;
    }

    public void setParkingMeters(Set<ParkingMeter> parkingMeters) {
        this.parkingMeters = parkingMeters;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area")
    public Set<ParkingUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ParkingUser> users) {
        this.users = users;
    }
}
