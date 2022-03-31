package domain.fare.one_way_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicFareForChild {
    @Getter
    private final int value;

    public BasicFareForChild discount(int discountValue) {
        BasicFareForChild discountedBasicFareForChild = new BasicFareForChild((int) ((double) value * (1 - ((double) discountValue / 100))));
        int unitDigit = discountedBasicFareForChild.getValue() % 10;
        return new BasicFareForChild(discountedBasicFareForChild.getValue() - unitDigit);
    }

    public BasicFareForChild two_times() {
        return new BasicFareForChild(2 * getValue());
    }
}
