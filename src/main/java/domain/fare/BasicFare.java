package domain.fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicFare {
    @Getter
    private final int value;

    public BasicFare truncate() {
        int unitDigit = value % 10;
        return new BasicFare(value - unitDigit);
    }

    public BasicFare discount(int discountValue) {
        return new BasicFare((int) ((double) value * ((double) discountValue / 100)));
    }
}
