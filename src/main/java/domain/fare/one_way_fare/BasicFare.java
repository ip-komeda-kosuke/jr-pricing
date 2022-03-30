package domain.fare.one_way_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicFare {
    @Getter
    private final int value;

    public BasicFare discount(int discountValue) {
        BasicFare discountedBasicFare = new BasicFare((int) ((double) value * (1 - ((double) discountValue / 100))));
        int unitDigit = discountedBasicFare.getValue() % 10;
        return new BasicFare(discountedBasicFare.getValue() - unitDigit);
    }

    public BasicFare two_times() {
        return new BasicFare(2 * getValue());
    }
}
