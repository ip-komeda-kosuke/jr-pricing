package domain.fare.option_fare;

import domain.fare.Fare;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class ExtraFare {
    @Getter
    private final Fare fare;
}
