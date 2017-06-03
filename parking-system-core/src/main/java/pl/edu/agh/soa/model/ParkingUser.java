package pl.edu.agh.soa.model;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;

/**
 * @author Mateusz Kluska
 */
@Entity
@Table(name = "parking_user", schema = "parking")
@NamedQueries({
        @NamedQuery(name = "User.findByArea",
                query = "select u from ParkingUser u where u.area = :area")
})
public class ParkingUser {
    private long id;
    private String username;
    private String password;
    private UserType userType;
    private Area area;

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
        String encPass = BCrypt.hashpw(password, BCrypt.gensalt());
        this.password = encPass;
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
}
