package pl.edu.agh.soa.contracts;

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
public class PlaceDTO {
    private long placeNumber;
    private boolean isBusy;
    private boolean isPaid;
}
