package domain.fare.round_trip_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RoundTripBasicFare {
    @Getter
    private final int value;

    public RoundTripBasicFare truncate() {
        int unitDigit = value % 10;
        return new RoundTripBasicFare(value - unitDigit);
    }

    public RoundTripBasicFare discount(int discountValue) {
        return new RoundTripBasicFare((int) ((double) value * (1 - ((double) discountValue / 100))));
    }
}
