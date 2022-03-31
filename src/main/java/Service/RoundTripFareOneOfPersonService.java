package Service;

import domain.child_option.ChildOption;
import domain.discount.RoundTripDiscountPolicy;
import domain.distance.Distance;
import domain.fare.one_way_fare.BasicFare;
import domain.fare.one_way_fare.BasicFareForChild;
import domain.fare.one_way_fare.SuperExpressSurcharge;
import domain.fare.one_way_fare.SuperExpressSurchargeForChild;
import domain.fare.round_trip_fare.*;
import domain.seat_type.SeatType;
import domain.station.DepartureAndDestination;
import domain.super_express_type.SuperExpressType;
import lombok.AllArgsConstructor;
import repository.IFareRepository;

@AllArgsConstructor
public class RoundTripFareOneOfPersonService {
    IFareRepository fareRepository;

    public RoundTripFare calculateRoundTripFare(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType, ChildOption childOption) {
        if (childOption.isChild()) {
            return new RoundTripFare(calculateChildFareForRoundTrip(departureAndDestination, superExpressType, seatType).getValue());
        } else {
            return new RoundTripFare(calculateAdultFareForRoundTrip(departureAndDestination, superExpressType, seatType).getValue());
        }
    }

    private RoundTripSuperExpressSurcharge calculateRoundTripSuperExpressSurcharge(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        SuperExpressSurcharge superExpressSurcharge = new FareForOnePersonService(fareRepository).calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new RoundTripSuperExpressSurcharge(superExpressSurcharge.two_times().getValue());
    }

    private RoundTripSuperExpressSurchargeForChild calculateRoundTripSuperExpressSurchargeForChild(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        SuperExpressSurchargeForChild superExpressSurchargeForChild = new FareForOnePersonService(fareRepository).calculateSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new RoundTripSuperExpressSurchargeForChild(superExpressSurchargeForChild.two_times().getValue());
    }

    private RoundTripAdultFare calculateAdultFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        RoundTripBasicFare roundTripBasicFare = calculateRoundTripBasicFare(departureAndDestination);
        RoundTripSuperExpressSurcharge roundTripSuperExpressSurcharge = calculateRoundTripSuperExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new RoundTripAdultFare(roundTripBasicFare.getValue() + roundTripSuperExpressSurcharge.getValue());
    }

    private RoundTripChildFare calculateChildFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        RoundTripBasicFareForChild roundTripBasicFareForChild = calculateRoundTripBasicFareForChild(departureAndDestination);
        RoundTripSuperExpressSurchargeForChild superExpressSurchargeForChild = calculateRoundTripSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new RoundTripChildFare(roundTripBasicFareForChild.getValue() + superExpressSurchargeForChild.getValue());
    }

    private RoundTripBasicFare calculateRoundTripBasicFare(DepartureAndDestination departureAndDestination) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        if (isRoundTripDiscount(distance)) {
            return new RoundTripBasicFare(basicFare.discount(10).two_times().getValue());
        } else {
            return new RoundTripBasicFare(basicFare.two_times().getValue());
        }
    }

    private RoundTripBasicFareForChild calculateRoundTripBasicFareForChild(DepartureAndDestination departureAndDestination) {
        BasicFareForChild basicFareForChild = new FareForOnePersonService(fareRepository).calculateBasicFareForChild(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        if (isRoundTripDiscount(distance)) {
            return new RoundTripBasicFareForChild(basicFareForChild.discount(10).two_times().getValue());
        } else {
            return new RoundTripBasicFareForChild(basicFareForChild.two_times().getValue());
        }
    }

    private boolean isRoundTripDiscount(Distance distance) {
        return distance.getValue() >= RoundTripDiscountPolicy.getDISTANCE().getValue();
    }
}
