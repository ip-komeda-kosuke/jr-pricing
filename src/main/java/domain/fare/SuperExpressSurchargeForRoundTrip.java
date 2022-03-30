package domain.fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SuperExpressSurchargeForRoundTrip {
    @Getter
    private final int value;

    public SuperExpressSurchargeForRoundTrip truncate() {
        int unitDigit = value % 10;
        return new SuperExpressSurchargeForRoundTrip(value - unitDigit);
    }

    public SuperExpressSurchargeForRoundTrip discount(int discountValue) {
        return new SuperExpressSurchargeForRoundTrip((int) ((double) value * ((double) discountValue / 100)));
    }
}
