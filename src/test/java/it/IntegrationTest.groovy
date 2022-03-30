package it

import Service.FareForOnePersonService
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
                new ChildOption(false)).getValue() == 14400
    }

    def "TOKYO から #destination まで #superExpressType #seatType 大人1枚"() {
        when:
        def actualFare = new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(
                new DepartureAndDestination(Station.TOKYO, destination),
                superExpressType, seatType, new ChildOption(false)).getValue()

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
                superExpressType, seatType, new ChildOption(true)).getValue()

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
}




