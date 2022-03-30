package domain.fare.round_trip_fare;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class RoundTripFare {
    @Getter
    private final int value;
}
