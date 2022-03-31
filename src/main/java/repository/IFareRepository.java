package repository;

import domain.distance.Distance;
import domain.fare.one_way_fare.BasicFare;
import domain.fare.option_fare.ExtraFare;
import domain.fare.one_way_fare.SuperExpressFareForReservedSeat;
import domain.station.DepartureAndDestination;

public interface IFareRepository {
    BasicFare findBasicFare(DepartureAndDestination departureAndDestination);

    SuperExpressFareForReservedSeat findSuperExpressFare(DepartureAndDestination departureAndDestination);

    ExtraFare findExtraFare(DepartureAndDestination departureAndDestination);

    Distance findDistance(DepartureAndDestination departureAndDestination);
}
