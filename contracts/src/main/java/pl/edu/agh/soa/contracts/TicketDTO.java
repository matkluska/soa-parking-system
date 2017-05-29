package pl.edu.agh.soa.contracts;


import lombok.*;

/**
 * @author Mateusz Kluska
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    @NonNull
    private long parkingMeterId;
    @NonNull
    private String carId;
    @NonNull
    private long durationInMinutes;
    @NonNull
    private long startTimeInMinutes;
}
