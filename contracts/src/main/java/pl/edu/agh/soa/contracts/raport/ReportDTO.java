package pl.edu.agh.soa.contracts.raport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class ReportDTO {
    private String reportStartTime;
    private long numberOfIncidents;
    private TicketReportDTO tickets;
    private Set<PlaceReportDTO> places = new HashSet<>();
    private Set<UserReportDTO> users = new HashSet<>();
    private Set<AreaReportDTO> areas = new HashSet<>();
}
