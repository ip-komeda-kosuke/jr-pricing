package domain.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscountRateForRoundTripFarePolicy {
    @Getter
    private static final DiscountRateForRoundTripFare discountRateForRoundTripFare = new DiscountRateForRoundTripFare(new Rate(10));
}
