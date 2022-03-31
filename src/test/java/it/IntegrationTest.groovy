package it

import Service.FareForOnePersonService
import Service.RoundTripFareOneOfPersonService
import domain.child_option.ChildOption
import domain.seat_type.SeatType
import domain.station.DepartureAndDestination
import domain.station.Station
import domain.super_express_type.SuperExpressType
import repository.FareRepository
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class IntegrationTest extends Specification {
    def "東京 から 新大阪 まで ひかり 指定席 大人片道1枚"() {
        expect:
        new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(
                new DepartureAndDestination(Station.TOKYO, Station.SHINOSAKA),
                SuperExpressType.HIKARI, SeatType.RESERVED_SEAT,
                new ChildOption(false)).getFare().getValue() == 14400
    }

    def "TOKYO から #destination まで #superExpressType #seatType 大人1枚"() {
        when:
        def actualFare = new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(
                new DepartureAndDestination(Station.TOKYO, destination),
                superExpressType, seatType, new ChildOption(false)).getFare().getValue()

        then:
        actualFare == expectedFare

        where:
        destination       | superExpressType        | seatType               || expectedFare
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT || 14400
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.FREE_SEAT     || 13870
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT || 14720
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     || 13870
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT || 15930
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.FREE_SEAT     || 15400
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT || 16460
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     || 15400
    }

    def "TOKYO から #destination まで #superExpressType #seatType 子供1枚"() {
        when:
        def actualFare = new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(
                new DepartureAndDestination(Station.TOKYO, destination),
                superExpressType, seatType, new ChildOption(true)).getFare().getValue()

        then:
        actualFare == expectedFare

        where:
        destination       | superExpressType        | seatType               || expectedFare
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT || 7190
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.FREE_SEAT     || 6930
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT || 7350
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     || 6930
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT || 7960
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.FREE_SEAT     || 7690
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT || 8220
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     || 7690
    }

    def "TOKYO から #destination まで #superExpressType #seatType 子供オプション #isChild 往復"() {
        when:
        def actualFare = new RoundTripFareOneOfPersonService(new FareRepository()).calculateRoundTripFare(
                new DepartureAndDestination(Station.TOKYO, destination),
                superExpressType, seatType, new ChildOption(isChild)).getFare().getValue()

        then:
        actualFare == expectedFare

        where:
        destination       | superExpressType        | seatType               | isChild || expectedFare
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT | false   || 28800
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.FREE_SEAT     | false   || 27740
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT | false   || 29440
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     | false   || 27740
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT | false   || 29840
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.FREE_SEAT     | false   || 28780
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT | false   || 30900
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     | false   || 28780

        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT | true    || 14380
        Station.SHINOSAKA | SuperExpressType.HIKARI | SeatType.FREE_SEAT     | true    || 13860
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT | true    || 14700
        Station.SHINOSAKA | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     | true    || 13860
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.RESERVED_SEAT | true    || 14920
        Station.HIMEJI    | SuperExpressType.HIKARI | SeatType.FREE_SEAT     | true    || 14380
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.RESERVED_SEAT | true    || 15440
        Station.HIMEJI    | SuperExpressType.NOZOMI | SeatType.FREE_SEAT     | true    || 14380
    }
}




