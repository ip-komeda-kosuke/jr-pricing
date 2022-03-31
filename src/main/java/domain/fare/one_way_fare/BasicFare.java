package domain.fare.one_way_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BasicFare {
    @Getter
    private final Fare fare;
}
