package domain.discount;

import domain.fare.Fare;
import lombok.Getter;

public class DiscountForFreeSeatPolicy {
    @Getter
    private static final DiscountForFreeSeat DISCOUNT_FOR_FREE_SEAT = new DiscountForFreeSeat(new Fare(-530));
}
