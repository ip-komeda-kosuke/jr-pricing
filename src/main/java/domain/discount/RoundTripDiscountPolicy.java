package domain.discount;

import domain.distance.Distance;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RoundTripDiscountPolicy {
    @Getter
    private static final Distance DISTANCE = new Distance(601);
}
