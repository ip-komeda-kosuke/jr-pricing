package domain.fare.one_way_fare;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class ChildFare {
    @Getter
    private final int value;
}
