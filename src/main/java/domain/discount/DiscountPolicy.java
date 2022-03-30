package domain.discount;

import lombok.Getter;

public class DiscountPolicy {
    @Getter
    private static final DiscountForFreeSeat DISCOUNT_FOR_FREE_SEAT = new DiscountForFreeSeat(530);
}
