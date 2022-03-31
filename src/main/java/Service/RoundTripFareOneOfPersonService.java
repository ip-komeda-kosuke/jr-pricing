package Service;

import domain.child_option.ChildOption;
import domain.distance.Distance;
import domain.fare.one_way_fare.BasicFare;
import domain.fare.one_way_fare.SuperExpressSurcharge;
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

    private RoundTripSuperExpressSurcharge calculateSuperExpressSurchargeForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType, ChildOption childOption) {
        SuperExpressSurcharge superExpressSurcharge = new FareForOnePersonService(fareRepository).calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        if(childOption.isChild()){
            superExpressSurcharge = superExpressSurcharge.discount(50);
            return new RoundTripSuperExpressSurcharge(superExpressSurcharge.two_times().getValue());
        }else {
            return new RoundTripSuperExpressSurcharge(superExpressSurcharge.two_times().getValue());
        }
    }

    private RoundTripAdultFare calculateAdultFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        RoundTripBasicFare roundTripBasicFare = calculateBasicFareForRoundTrip(departureAndDestination, new ChildOption(false));
        RoundTripSuperExpressSurcharge roundTripSuperExpressSurcharge = calculateSuperExpressSurchargeForRoundTrip(departureAndDestination, superExpressType, seatType, new ChildOption(false));
        return new RoundTripAdultFare(roundTripBasicFare.getValue() + roundTripSuperExpressSurcharge.getValue());
    }

    private RoundTripChildFare calculateChildFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        RoundTripBasicFare basicFareFareForChild = calculateBasicFareForRoundTrip(departureAndDestination, new ChildOption(true));
        RoundTripSuperExpressSurcharge superExpressSurchargeForChild = calculateSuperExpressSurchargeForRoundTrip(departureAndDestination, superExpressType, seatType, new ChildOption(true));
        return new RoundTripChildFare(basicFareFareForChild.getValue() + superExpressSurchargeForChild.getValue());
    }

    private RoundTripBasicFare calculateBasicFareForRoundTrip(BasicFare basicFare, Distance distance, ChildOption childOption) {
        if (childOption.isChild()) {
            BasicFare basicFareForChild = basicFare.discount(50);
            if (distance.getValue() >= 601) {
                return new RoundTripBasicFare(basicFareForChild.discount(10).two_times().getValue());
            } else {
                return new RoundTripBasicFare(basicFareForChild.two_times().getValue());
            }
        } else {
            if (distance.getValue() >= 601) {
                return new RoundTripBasicFare(basicFare.discount(10).two_times().getValue());
            } else {
                return new RoundTripBasicFare(basicFare.two_times().getValue());
            }
        }
    }

    private RoundTripBasicFare calculateBasicFareForRoundTrip(DepartureAndDestination departureAndDestination, ChildOption childOption) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        return calculateBasicFareForRoundTrip(basicFare, distance, childOption);
    }
}
