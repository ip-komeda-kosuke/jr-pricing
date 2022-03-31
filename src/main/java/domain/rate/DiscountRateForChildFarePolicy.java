package domain.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscountRateForChildFarePolicy {
    @Getter
    private static final DiscountRateForChildFare DISCOUNT_RATE_FOR_CHILD_FARE = new DiscountRateForChildFare(new Rate(50));
}
