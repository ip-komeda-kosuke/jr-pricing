import Service.FareForOnePersonService;
import domain.child_option.ChildOption;
import domain.seat_type.SeatType;
import domain.station.DepartureAndDestination;
import domain.station.Station;
import domain.super_express_type.SuperExpressType;
import repository.FareRepository;

public class Main {
    public static void main(String[] args) {
//        System.out.println(new FareRepository().findBasicFare(new DepartureAndDestination(Station.TOKYO,Station.SHINOSAKA)).getValue());
//        System.out.println(new FareRepository().findSuperExpressFare(new DepartureAndDestination(Station.TOKYO,Station.SHINOSAKA)).getValue());
//        System.out.println(new FareRepository().findExtraFare(new DepartureAndDestination(Station.TOKYO,Station.SHINOSAKA)).getValue());
//
//        System.out.println(new FareForOnePersonService(new FareRepository()).calculateFareForOnePerson(new DepartureAndDestination(Station.TOKYO, Station.SHINOSAKA), SuperExpressType.NOZOMI, SeatType.RESERVED_SEAT, new ChildOption(false)).getValue());
        System.out.println(Station.TOKYO.toString());
    }
}
