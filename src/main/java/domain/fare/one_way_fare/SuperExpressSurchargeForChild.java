package domain.fare.one_way_fare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SuperExpressSurchargeForChild {
    @Getter
    private final int value;

    public SuperExpressSurchargeForChild discount(int discountValue) {
        SuperExpressSurchargeForChild discountedSuperExpressSurchargeForChild = new SuperExpressSurchargeForChild((int) ((double) value * (1 - ((double) discountValue / 100))));
        int unitDigit = discountedSuperExpressSurchargeForChild.getValue() % 10;
        return new SuperExpressSurchargeForChild(discountedSuperExpressSurchargeForChild.getValue() - unitDigit);
    }

    public SuperExpressSurchargeForChild two_times() {
        return new SuperExpressSurchargeForChild(2 * getValue());
    }
}
