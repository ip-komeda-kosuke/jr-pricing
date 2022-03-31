package domain.fare.round_trip_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RoundTripSuperExpressSurcharge {
    @Getter
    private final int value;

    public RoundTripSuperExpressSurcharge truncate() {
        int unitDigit = value % 10;
        return new RoundTripSuperExpressSurcharge(value - unitDigit);
    }

    public RoundTripSuperExpressSurcharge discount(int discountValue) {
        return new RoundTripSuperExpressSurcharge((int) ((double) value * ((double) discountValue / 100)));
    }
}
