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
    private List<Integer> parkingMeterIds = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    private int parkingMeterId;
    private String carId;
    private long durationInMinutes;

    @EJB
    private DataSender dataSender;

    public int getParkingMeterId() {
        return parkingMeterId;
    }

    public void setParkingMeterId(int parkingMeterId) {
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

    public List<Integer> getParkingMeterIds() {
        return parkingMeterIds;
    }

    public void setParkingMeterIds(List<Integer> parkingMeterIds) {
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
