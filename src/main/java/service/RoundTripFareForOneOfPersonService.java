package service;

import domain.child_option.ChildOption;
import domain.discount.RoundTripDiscountPolicy;
import domain.distance.Distance;
import domain.fare.Fare;
import domain.fare.one_way_fare.BasicFare;
import domain.fare.one_way_fare.BasicFareForChild;
import domain.fare.one_way_fare.SuperExpressSurcharge;
import domain.fare.one_way_fare.SuperExpressSurchargeForChild;
import domain.fare.round_trip_fare.*;
import domain.rate.DiscountRateForRoundTripFarePolicy;
import domain.seat_type.SeatType;
import domain.station.DepartureAndDestination;
import domain.super_express_type.SuperExpressType;
import lombok.AllArgsConstructor;
import repository.IFareRepository;

@AllArgsConstructor
public class RoundTripFareForOneOfPersonService {
    IFareRepository fareRepository;

    public RoundTripFare calculateRoundTripFare(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType,
            ChildOption childOption
    ) {
        if (childOption.isChild()) {
            return new RoundTripFare(calculateChildFareForRoundTrip(
                    departureAndDestination,
                    superExpressType,
                    seatType).getFare());
        } else {
            return new RoundTripFare(calculateAdultFareForRoundTrip(
                    departureAndDestination,
                    superExpressType,
                    seatType).getFare());
        }
    }

    private RoundTripSuperExpressSurcharge calculateRoundTripSuperExpressSurcharge(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType) {
        SuperExpressSurcharge superExpressSurcharge
                = new OneWayFareForOneOfPersonService(fareRepository)
                .calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new RoundTripSuperExpressSurcharge(superExpressSurcharge.getFare().two_times());
    }

    private RoundTripSuperExpressSurchargeForChild calculateRoundTripSuperExpressSurchargeForChild(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        SuperExpressSurchargeForChild superExpressSurchargeForChild
                = new OneWayFareForOneOfPersonService(fareRepository)
                .calculateSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new RoundTripSuperExpressSurchargeForChild(superExpressSurchargeForChild.getFare().two_times());
    }

    private RoundTripAdultFare calculateAdultFareForRoundTrip(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        RoundTripBasicFare roundTripBasicFare = calculateRoundTripBasicFareForAdult(departureAndDestination);
        RoundTripSuperExpressSurcharge roundTripSuperExpressSurcharge
                = calculateRoundTripSuperExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new RoundTripAdultFare(roundTripBasicFare.getFare().plus(roundTripSuperExpressSurcharge.getFare()));
    }

    private RoundTripChildFare calculateChildFareForRoundTrip(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        RoundTripBasicFareForChild roundTripBasicFareForChild
                = calculateRoundTripBasicFareForChild(departureAndDestination);
        RoundTripSuperExpressSurchargeForChild roundTripSuperExpressSurchargeForChild
                = calculateRoundTripSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new RoundTripChildFare(roundTripBasicFareForChild.getFare()
                .plus(roundTripSuperExpressSurchargeForChild.getFare()));
    }

    private RoundTripBasicFare calculateRoundTripBasicFareForAdult(DepartureAndDestination departureAndDestination) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        return new RoundTripBasicFare(calculateRoundTripBasicFareForAdult(distance, basicFare.getFare()));
    }

    private RoundTripBasicFareForChild calculateRoundTripBasicFareForChild(DepartureAndDestination departureAndDestination) {
        BasicFareForChild basicFareForChild
                = new OneWayFareForOneOfPersonService(fareRepository).calculateBasicFareForChild(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        return new RoundTripBasicFareForChild(calculateRoundTripBasicFareForAdult(distance, basicFareForChild.getFare()));
    }

    private Fare calculateRoundTripBasicFareForAdult(Distance distance, Fare fare) {
        if (distance.getValue() >= RoundTripDiscountPolicy.getDISTANCE().getValue()) {
            return fare.discount(DiscountRateForRoundTripFarePolicy.getDiscountRateForRoundTripFare().getRate()).two_times();
        } else {
            return fare.two_times();
        }
    }
}
