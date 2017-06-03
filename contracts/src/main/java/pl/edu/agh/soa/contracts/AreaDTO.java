package pl.edu.agh.soa.contracts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class AreaDTO {
    private Long areaNumber;
    private Set<PlaceDTO> places;
}
