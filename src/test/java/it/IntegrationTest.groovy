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
        new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(new DepartureAndDestination(Station.TOKYO, Station.SHINOSAKA), SuperExpressType.HIKARI, SeatType.RESERVED_SEAT, new ChildOption(false)).getValue() == 14400
    }

    def "東京 から #station まで #train #seat 大人片道1枚"() {
        when:
        def actualFare = getFareForOneOfPerson(station, train, seat)

        then:
        actualFare == expectedFare

        where:
        station | train | seat  || expectedFare
        "新大阪"   | "ひかり" | "指定席" || 14400
        "新大阪"   | "ひかり" | "自由席" || 13870
        "新大阪"   | "のぞみ" | "指定席" || 14720
        "新大阪"   | "のぞみ" | "自由席" || 13870
        "姫路"    | "ひかり" | "指定席" || 15930
        "姫路"    | "ひかり" | "自由席" || 15400
        "姫路"    | "のぞみ" | "指定席" || 16460
        "姫路"    | "のぞみ" | "自由席" || 15400
    }

    private static int getFareForOneOfPerson(String station, String train, String seat) {
        DepartureAndDestination departureAndDestination
        SuperExpressType superExpressType
        SeatType seatType
        switch (station) {
            case "新大阪":
                departureAndDestination = new DepartureAndDestination(Station.TOKYO, Station.SHINOSAKA)
                break
            case "姫路":
                departureAndDestination = new DepartureAndDestination(Station.TOKYO, Station.HIMEJI)
                break
            default:
                throw new RuntimeException("指定した駅名はありません。")
        }

        switch (train) {
            case "ひかり":
                superExpressType = SuperExpressType.HIKARI
                break
            case "のぞみ":
                superExpressType = SuperExpressType.NOZOMI
                break
            default:
                throw new RuntimeException("指定した新幹線はありません。")
        }

        switch (seat) {
            case "指定席":
                seatType = SeatType.RESERVED_SEAT
                break
            case "自由席":
                seatType = SeatType.FREE_SEAT
                break
            default:
                throw new RuntimeException("指定した席はありません。")
        }
        return new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(departureAndDestination, superExpressType, seatType, new ChildOption(false)).getValue()
    }

}




