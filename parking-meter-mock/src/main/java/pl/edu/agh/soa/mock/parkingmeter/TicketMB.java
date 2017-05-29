package pl.edu.agh.soa.mock.parkingmeter;

import com.google.common.collect.Lists;
import pl.edu.agh.soa.contracts.TicketDTO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class TicketMB {
    private List<Long> parkingMeterIds = Lists.newArrayList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L);
    private long parkingMeterId;
    private String carId;
    private long durationInMinutes;

    @EJB
    private DataSender dataSender;

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

    public void sendData() throws IOException {
        if (Objects.nonNull(carId) && parkingMeterId != 0
                && durationInMinutes != 0) {
            dataSender.sendTicket(getTicket());

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
                .startTimeInMinutes(currentMinutes)
                .build();
    }
}
