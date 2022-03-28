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
        BasicFare basicFare;
        BufferedReader br;
        String file = "data/charge_list.csv";
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String[] rowData;
            String[] column;
            column = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                rowData = line.split(",");
                if (rowData[0].equals(departureAndDestination.getDeparture().toString())) {
                    for (int i = 1; i < column.length; i++) {
                        if (column[i].equals(departureAndDestination.getDestination().toString())) {
                            basicFare = new BasicFare(Integer.parseInt(rowData[i]));
                            return basicFare;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SuperExpressFareForReservedSeat findSuperExpressFare(DepartureAndDestination departureAndDestination) {
        SuperExpressFareForReservedSeat superExpressFareForReservedSeat;
        BufferedReader br;
        String file = "data/super_express_surcharge_list.csv";
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String[] rowData;
            String[] column;
            column = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                rowData = line.split(",");
                if (rowData[0].equals(departureAndDestination.getDeparture().toString())) {
                    for (int i = 1; i < column.length; i++) {
                        if (column[i].equals(departureAndDestination.getDestination().toString())) {
                            superExpressFareForReservedSeat = new SuperExpressFareForReservedSeat(Integer.parseInt(rowData[i]));
                            return superExpressFareForReservedSeat;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ExtraFare findExtraFare(DepartureAndDestination departureAndDestination) {
        ExtraFare extraFare;
        BufferedReader br;
        String file = "data/extra_charge_list.csv";
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            String[] rowData;
            String[] column;
            column = br.readLine().split(",");
            while ((line = br.readLine()) != null) {
                rowData = line.split(",");
                if (rowData[0].equals(departureAndDestination.getDeparture().toString())) {
                    for (int i = 1; i < column.length; i++) {
                        if (column[i].equals(departureAndDestination.getDestination().toString())) {
                            extraFare = new ExtraFare(Integer.parseInt(rowData[i]));
                            return extraFare;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
