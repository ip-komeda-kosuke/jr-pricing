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

    def "東京 から #station まで #train #seat 子供片道1枚"() {
        when:
        def actualFare = getFareForChild(station, train, seat)

        then:
        actualFare == expectedFare

        where:
        station | train | seat  || expectedFare
        "新大阪"   | "ひかり" | "指定席" || 7190
        "新大阪"   | "ひかり" | "自由席" || 6930
        "新大阪"   | "のぞみ" | "指定席" || 7350
        "新大阪"   | "のぞみ" | "自由席" || 6930
        "姫路"    | "ひかり" | "指定席" || 7960
        "姫路"    | "ひかり" | "自由席" || 7690
        "姫路"    | "のぞみ" | "指定席" || 8220
        "姫路"    | "のぞみ" | "自由席" || 7690
    }

    private static int getFareForOneOfPerson(String station, String train, String seat) {
        DepartureAndDestination departureAndDestination = new DepartureAndDestination(Station.TOKYO, convertTypeForStation(station))

        SuperExpressType superExpressType = convertTypeForTrain(train)

        SeatType seatType = convertTypeForSeat(seat)

        return new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(departureAndDestination, superExpressType, seatType, new ChildOption(false)).getValue()
    }

    private static int getFareForChild(String station, String train, String seat) {
        DepartureAndDestination departureAndDestination = new DepartureAndDestination(Station.TOKYO, convertTypeForStation(station))

        SuperExpressType superExpressType = convertTypeForTrain(train)

        SeatType seatType = convertTypeForSeat(seat)

        return new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(departureAndDestination, superExpressType, seatType, new ChildOption(true)).getValue()
    }

    private static Station convertTypeForStation(String value) {
        switch (value) {
            case "新大阪":
                return Station.SHINOSAKA
                break
            case "姫路":
                return Station.HIMEJI
                break
            default:
                throw new RuntimeException("指定した駅名はありません。")
        }
    }

    private static SuperExpressType convertTypeForTrain(String value) {
        switch (value) {
            case "ひかり":
                return SuperExpressType.HIKARI
                break
            case "のぞみ":
                return SuperExpressType.NOZOMI
                break
            default:
                throw new RuntimeException("指定した新幹線はありません。")
        }
    }

    private static SeatType convertTypeForSeat(String value) {
        switch (value) {
            case "指定席":
                return SeatType.RESERVED_SEAT
                break
            case "自由席":
                return SeatType.FREE_SEAT
                break
            default:
                throw new RuntimeException("指定した席はありません。")
        }
    }

}




