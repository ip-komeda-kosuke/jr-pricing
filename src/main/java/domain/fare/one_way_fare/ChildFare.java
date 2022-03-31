package domain.fare.one_way_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class ChildFare {
    @Getter
    private final Fare fare;
}
