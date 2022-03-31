package domain.fare.round_trip_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class RoundTripFare {
    @Getter
    private final Fare fare;
}
