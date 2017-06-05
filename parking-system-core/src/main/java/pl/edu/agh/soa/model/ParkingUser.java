package pl.edu.agh.soa.model;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(name = "parking_user", schema = "parking")
@NamedQueries({
        @NamedQuery(name = "User.findByArea",
                query = "select u from ParkingUser u where u.area = :area"),
        @NamedQuery(name = "User.findWithIncidentsBefore",
                query = "select u from ParkingUser u join fetch u.incidents i where i.timeInMillis > :time")
})
public class ParkingUser {
    private long id;
    private String username;
    private String password;
    private UserType userType;
    private Area area;
    private Set<Incident> incidents = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "USERNAME", unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AREA_ID")
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingUser")
    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }
}
