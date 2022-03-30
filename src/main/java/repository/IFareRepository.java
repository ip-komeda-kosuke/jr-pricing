package repository;

import domain.distance.Distance;
import domain.fare.BasicFare;
import domain.fare.ExtraFare;
import domain.fare.SuperExpressFareForReservedSeat;
import domain.station.DepartureAndDestination;

public interface IFareRepository {
    BasicFare findBasicFare(DepartureAndDestination departureAndDestination);

    SuperExpressFareForReservedSeat findSuperExpressFare(DepartureAndDestination departureAndDestination);

    ExtraFare findExtraFare(DepartureAndDestination departureAndDestination);

    Distance findDistance(DepartureAndDestination departureAndDestination);
}
