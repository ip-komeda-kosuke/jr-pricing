package domain.station;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DepartureAndDestination {
    @Getter
    private Station departure;
    @Getter
    private Station destination;
}
