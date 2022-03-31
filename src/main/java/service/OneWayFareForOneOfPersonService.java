package service;

import domain.child_option.ChildOption;
import domain.discount.DiscountForFreeSeat;
import domain.discount.DiscountForFreeSeatPolicy;
import domain.fare.one_way_fare.*;
import domain.fare.option_fare.ExtraFare;
import domain.rate.DiscountRateForChildFarePolicy;
import domain.seat_type.SeatType;
import domain.station.DepartureAndDestination;
import domain.super_express_type.SuperExpressType;
import lombok.AllArgsConstructor;
import repository.IFareRepository;

@AllArgsConstructor
public class OneWayFareForOneOfPersonService {
    private IFareRepository fareRepository;

    public FareForOnePerson calculateFareForOnePerson(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType,
            ChildOption childOption
    ) {
        if (childOption.isChild()) {
            return new FareForOnePerson(calculateChildFare(departureAndDestination, superExpressType, seatType).getFare());

        } else {
            return new FareForOnePerson(calculateAdultFare(departureAndDestination, superExpressType, seatType).getFare());
        }
    }

    SuperExpressSurcharge calculateExpressSurcharge(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        SuperExpressSurcharge superExpressSurcharge = new SuperExpressSurcharge(
                fareRepository.findSuperExpressFare(departureAndDestination).getFare());

        if (superExpressType == SuperExpressType.NOZOMI && seatType == SeatType.RESERVED_SEAT) {
            ExtraFare extraFare = fareRepository.findExtraFare(departureAndDestination);
            superExpressSurcharge = new SuperExpressSurcharge(superExpressSurcharge.getFare().plus(extraFare.getFare()));
            return superExpressSurcharge;
        }

        if (seatType == SeatType.FREE_SEAT) {
            DiscountForFreeSeat discountForFreeSeat = DiscountForFreeSeatPolicy.getDISCOUNT_FOR_FREE_SEAT();
            superExpressSurcharge = new SuperExpressSurcharge(superExpressSurcharge.getFare().plus(discountForFreeSeat.getFare()));
            return superExpressSurcharge;
        }

        return superExpressSurcharge;
    }

    BasicFareForChild calculateBasicFareForChild(DepartureAndDestination departureAndDestination) {
        return new BasicFareForChild(fareRepository
                .findBasicFare(departureAndDestination)
                .getFare()
                .discount(DiscountRateForChildFarePolicy.getDISCOUNT_RATE_FOR_CHILD_FARE().getRate()));
    }

    SuperExpressSurchargeForChild calculateSuperExpressSurchargeForChild(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        return new SuperExpressSurchargeForChild(
                calculateExpressSurcharge(departureAndDestination, superExpressType, seatType)
                        .getFare().discount(DiscountRateForChildFarePolicy.getDISCOUNT_RATE_FOR_CHILD_FARE()
                        .getRate()));
    }

    private AdultFare calculateAdultFare(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        BasicFare basicFare = fareRepository.findBasicFare(departureAndDestination);
        SuperExpressSurcharge superExpressSurcharge
                = calculateExpressSurcharge(departureAndDestination, superExpressType, seatType);
        return new AdultFare(basicFare.getFare().plus(superExpressSurcharge.getFare()));
    }

    private ChildFare calculateChildFare(
            DepartureAndDestination departureAndDestination,
            SuperExpressType superExpressType,
            SeatType seatType
    ) {
        BasicFareForChild basicFareFareForChild = calculateBasicFareForChild(departureAndDestination);
        SuperExpressSurchargeForChild superExpressSurchargeForChild
                = calculateSuperExpressSurchargeForChild(departureAndDestination, superExpressType, seatType);
        return new ChildFare(basicFareFareForChild.getFare().plus(superExpressSurchargeForChild.getFare()));
    }
}
