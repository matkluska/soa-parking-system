package pl.edu.agh.soa.contracts.raport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class AreaReportDTO {
    private long areaNumber;
    private int numberOfTickets;
    private int numberOfIncidents;
    private long paidTimeInMinutes;
}
