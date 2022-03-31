package domain.fare.round_trip_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RoundTripChildFare {
    @Getter
    private final Fare fare;
}
