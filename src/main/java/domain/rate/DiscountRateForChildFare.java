package domain.rate;

import domain.rate.Rate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscountRateForChildFare{
    @Getter
    private final Rate rate;
}
