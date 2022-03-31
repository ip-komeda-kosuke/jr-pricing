package domain.fare;

import domain.fare.one_way_fare.BasicFare;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class Fare {
    @Getter
    private final int value;

    public Fare plus(Fare fare){
        return new Fare(value + fare.getValue());
    }

    public Fare discount(int discountValue) {
        Fare fare = new Fare((int) ((double) value * (1 - ((double) discountValue / 100))));
        int unitDigit = fare.getValue() % 10;
        return new Fare(fare.getValue() - unitDigit);
    }

    public Fare two_times() {
        return new Fare(2 * getValue());
    }
}
