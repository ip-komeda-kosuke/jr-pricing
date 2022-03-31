package domain.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscountRateForRoundTripFare {
    @Getter
    private final Rate rate;
}
