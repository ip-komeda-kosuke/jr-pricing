package domain.fare;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class SuperExpressSurcharge {
    @Getter
    private final int value;

    public SuperExpressSurcharge discount(int discountValue) {
        SuperExpressSurcharge discountedSuperExpressSurcharge = new SuperExpressSurcharge((int) ((double) value * (1 - ((double) discountValue / 100))));
        int unitDigit = discountedSuperExpressSurcharge.getValue() % 10;
        return new SuperExpressSurcharge(discountedSuperExpressSurcharge.getValue() - unitDigit);
    }
}
