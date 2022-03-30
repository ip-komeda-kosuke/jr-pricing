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

    private SuperExpressSurchargeForRoundTrip calculateSuperExpressSurchargeForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType, ChildOption childOption) {
        SuperExpressSurcharge superExpressSurcharge = new FareForOnePersonService(fareRepository).calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        if(childOption.isChild()){
            superExpressSurcharge = superExpressSurcharge.discount(50);
            return new SuperExpressSurchargeForRoundTrip(superExpressSurcharge.two_times().getValue());
        }else {
            return new SuperExpressSurchargeForRoundTrip(superExpressSurcharge.two_times().getValue());
        }
    }

    private AdultFareForRoundTrip calculateAdultFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        BasicFareForRoundTrip basicFareForRoundTrip = calculateBasicFareForRoundTrip(departureAndDestination, new ChildOption(false));
        SuperExpressSurchargeForRoundTrip superExpressSurchargeForRoundTrip = calculateSuperExpressSurchargeForRoundTrip(departureAndDestination, superExpressType, seatType, new ChildOption(false));
        return new AdultFareForRoundTrip(basicFareForRoundTrip.getValue() + superExpressSurchargeForRoundTrip.getValue());
    }

    private ChildFareForRoundTrip calculateChildFareForRoundTrip(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        BasicFareForRoundTrip basicFareFareForChild = calculateBasicFareForRoundTrip(departureAndDestination, new ChildOption(true));
        SuperExpressSurchargeForRoundTrip superExpressSurchargeForChild = calculateSuperExpressSurchargeForRoundTrip(departureAndDestination, superExpressType, seatType, new ChildOption(true));
        return new ChildFareForRoundTrip(basicFareFareForChild.getValue() + superExpressSurchargeForChild.getValue());
    }

    private BasicFareForRoundTrip calculateBasicFareForRoundTrip(BasicFare basicFare, Distance distance, ChildOption childOption) {
        if (childOption.isChild()) {
            BasicFare basicFareForChild = basicFare.discount(50);
            if (distance.getValue() >= 601) {
                return new BasicFareForRoundTrip(basicFareForChild.discount(10).two_times().getValue());
            } else {
                return new BasicFareForRoundTrip(basicFareForChild.two_times().getValue());
            }
        } else {
            if (distance.getValue() >= 601) {
                return new BasicFareForRoundTrip(basicFare.discount(10).two_times().getValue());
            } else {
                return new BasicFareForRoundTrip(basicFare.two_times().getValue());
            }
        }
    }

    private BasicFareForRoundTrip calculateBasicFareForRoundTrip(DepartureAndDestination departureAndDestination, ChildOption childOption) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        Distance distance = fareRepository.findDistance(departureAndDestination);
        return calculateBasicFareForRoundTrip(basicFare, distance, childOption);
    }
}
