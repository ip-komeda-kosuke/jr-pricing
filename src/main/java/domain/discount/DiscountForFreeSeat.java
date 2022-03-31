package domain.discount;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscountForFreeSeat {
    @Getter
    private final Fare fare;
}
