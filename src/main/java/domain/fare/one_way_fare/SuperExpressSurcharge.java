package domain.fare.one_way_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class SuperExpressSurcharge {
    @Getter
    private final Fare fare;
}
