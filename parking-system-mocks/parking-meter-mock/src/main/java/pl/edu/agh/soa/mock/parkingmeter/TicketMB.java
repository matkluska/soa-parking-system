package pl.edu.agh.soa.mock.parkingmeter;

import pl.edu.agh.soa.contracts.TicketDTO;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class TicketMB {
    private List<Long> parkingMeterIds = new ArrayList<>(25);
    private List<Long> placeIds = new ArrayList<>(250);
    private long parkingMeterId;
    private long placeId;
    private String carId;
    private long durationInMinutes;

    @EJB
    private ParkingMeterController parkingMeterController;

    @PostConstruct
    public void init() {
        for (long i = 1L; i <= 250L; i++) {
            placeIds.add(i);
        }
        for (long i = 1L; i <= 25L; i++) {
            parkingMeterIds.add(i);
        }

    }

    public long getParkingMeterId() {
        return parkingMeterId;
    }

    public void setParkingMeterId(long parkingMeterId) {
        this.parkingMeterId = parkingMeterId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public long getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(long durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public List<Long> getParkingMeterIds() {
        return parkingMeterIds;
    }

    public void setParkingMeterIds(List<Long> parkingMeterIds) {
        this.parkingMeterIds = parkingMeterIds;
    }

    public List<Long> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(List<Long> placeIds) {
        this.placeIds = placeIds;
    }

    public long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    public void sendData() throws IOException {
        if (Objects.nonNull(carId) && parkingMeterId != 0
                && durationInMinutes != 0) {
            parkingMeterController.sendTicket(getTicket());

            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
    }


    private TicketDTO getTicket() {
        long currentMinutes = System.currentTimeMillis() / 1000 / 60;
        return TicketDTO.builder()
                .carId(carId)
                .durationInMinutes(durationInMinutes)
                .parkingMeterId(parkingMeterId)
                .placeId(placeId)
                .startTimeInMinutes(currentMinutes)
                .build();
    }
}
