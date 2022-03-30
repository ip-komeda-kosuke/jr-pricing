package domain

import domain.fare.Fare
import spock.lang.Specification

class FareTest extends Specification {
    private static final expectedFare = new Fare(8910)

    def "fareTest"(){
        when:
        def actualFare = new Fare(8910)

        then:
        actualFare == expectedFare
    }
}
