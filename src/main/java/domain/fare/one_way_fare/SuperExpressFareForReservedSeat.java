package domain.fare.one_way_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuperExpressFareForReservedSeat {
    @Getter
    private final Fare fare;
}
