package Service;

import domain.child_option.ChildOption;
import domain.discount.DiscountPolicy;
import domain.fare.one_way_fare.*;
import domain.seat_type.SeatType;
import domain.station.DepartureAndDestination;
import domain.super_express_type.SuperExpressType;
import lombok.AllArgsConstructor;
import repository.IFareRepository;

@AllArgsConstructor
public class FareForOnePersonService {
    private IFareRepository fareRepository;

    public FareForOnePerson calculateFareForOnePerson(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType, ChildOption childOption) {
        if (childOption.isChild()) {
            return new FareForOnePerson(calculateChildFare(departureAndDestination, superExpressType, seatType).getValue());

        } else {
            return new FareForOnePerson(calculateAdultFare(departureAndDestination, superExpressType, seatType).getValue());
        }
    }

    SuperExpressSurcharge calculateExpressSurcharge(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        SuperExpressSurcharge superExpressSurcharge = new SuperExpressSurcharge(fareRepository.findSuperExpressFare(departureAndDestination).getValue());
        if (superExpressType == SuperExpressType.NOZOMI && seatType == SeatType.RESERVED_SEAT) {
            superExpressSurcharge = new SuperExpressSurcharge(superExpressSurcharge.getValue() + fareRepository.findExtraFare(departureAndDestination).getValue());
            return superExpressSurcharge;
        }

        if (seatType == SeatType.FREE_SEAT) {
            superExpressSurcharge = new SuperExpressSurcharge(superExpressSurcharge.getValue() - DiscountPolicy.getDISCOUNT_FOR_FREE_SEAT().getValue());
            return superExpressSurcharge;
        }
        return superExpressSurcharge;
    }

    BasicFareForChild calculateBasicFareForChild(DepartureAndDestination departureAndDestination) {
        return new BasicFareForChild(fareRepository.findBasicFare(departureAndDestination).discount(50).getValue());
    }

    private SuperExpressSurchargeForChild calculateSuperExpressSurchargeForChild(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        return new SuperExpressSurchargeForChild(calculateExpressSurcharge(departureAndDestination, superExpressType, seatType).discount(50).getValue());
    }

    private AdultFare calculateAdultFare(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        SuperExpressSurcharge superExpressSurcharge = calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new AdultFare(basicFare.getValue() + superExpressSurcharge.getValue());
    }

    private ChildFare calculateChildFare(DepartureAndDestination departureAndDestination, SuperExpressType superExpressType, SeatType seatType) {
        BasicFareForChild basicFareFareForChild = calculateBasicFareForChild(departureAndDestination);
        SuperExpressSurchargeForChild superExpressSurchargeForChild = calculateSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new ChildFare(basicFareFareForChild.getValue() + superExpressSurchargeForChild.getValue());
    }
}
