package pl.edu.agh.soa.parkingsensors.mock;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class ParkingSensorsMB {
    private final List<Long> placeIds = new ArrayList<>(250);

    @Inject
    PlaceSensorController placeSensorController;

    @PostConstruct
    public void init() {
        for (long i = 1L; i <= 250L; i++) {
            placeIds.add(i);
        }
    }

    public List<Long> getPlaceIds() {
        return placeIds;
    }

    public void togglePlace(long placeId) {
        placeSensorController.toggle(placeId);
    }
}

