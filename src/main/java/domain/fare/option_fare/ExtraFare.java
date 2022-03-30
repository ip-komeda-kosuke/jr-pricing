package domain.fare.option_fare;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class ExtraFare {
    @Getter
    private final int value;
}
