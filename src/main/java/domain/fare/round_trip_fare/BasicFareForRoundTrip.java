package domain.fare.round_trip_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicFareForRoundTrip {
    @Getter
    private final int value;

    public BasicFareForRoundTrip truncate() {
        int unitDigit = value % 10;
        return new BasicFareForRoundTrip(value - unitDigit);
    }

    public BasicFareForRoundTrip discount(int discountValue) {
        return new BasicFareForRoundTrip((int) ((double) value * (1 - ((double) discountValue / 100))));
    }
}
