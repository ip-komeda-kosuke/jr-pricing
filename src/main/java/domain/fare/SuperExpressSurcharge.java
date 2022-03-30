package domain.fare;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class SuperExpressSurcharge {
    @Getter
    private final int value;

    public SuperExpressSurcharge plus(SuperExpressSurcharge superExpressSurcharge){
        return new SuperExpressSurcharge(value + superExpressSurcharge.getValue());
    }

    public SuperExpressSurcharge truncate() {
        int unitDigit = value % 10;
        return new SuperExpressSurcharge(value - unitDigit);
    }

    public SuperExpressSurcharge discount(int discountValue) {
        return new SuperExpressSurcharge((int) ((double) value * ((double) discountValue / 100)));
    }

    public SuperExpressSurcharge two_times() {
        return new SuperExpressSurcharge(2 * getValue());
    }
}
