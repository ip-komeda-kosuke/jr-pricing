package repository;

import domain.fare.BasicFare;
import domain.fare.ExtraFare;
import domain.fare.SuperExpressFareForReservedSeat;
import domain.station.DepartureAndDestination;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FareRepository implements IFareRepository {
    @Override
    public BasicFare findBasicFare(DepartureAndDestination departureAndDestination) {
        String file = "data/charge_list.csv";
        String start = departureAndDestination.getDeparture().toString();
        String end = departureAndDestination.getDestination().toString();

        return new BasicFare(searchChargeFromCSV(file, start, end));
    }

    @Override
    public SuperExpressFareForReservedSeat findSuperExpressFare(DepartureAndDestination departureAndDestination) {
        String file = "data/super_express_surcharge_list.csv";
        String start = departureAndDestination.getDeparture().toString();
        String end = departureAndDestination.getDestination().toString();

        return new SuperExpressFareForReservedSeat(searchChargeFromCSV(file, start, end));
    }

    @Override
    public ExtraFare findExtraFare(DepartureAndDestination departureAndDestination) {
        String file = "data/extra_charge_list.csv";
        String start = departureAndDestination.getDeparture().toString();
        String end = departureAndDestination.getDestination().toString();

        return new ExtraFare(searchChargeFromCSV(file, start, end));
    }

    private int searchChargeFromCSV(String file, String column, String row) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String[] columns;
            String[] rowData;
            columns = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                rowData = line.split(",");
                if (rowData[0].equals(column)) {
                    for (int i = 1; i < columns.length; i++) {
                        if (columns[i].equals(row)) {
                            return Integer.parseInt(rowData[i]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("検索値が見つかりませんでした。");
    }
}
