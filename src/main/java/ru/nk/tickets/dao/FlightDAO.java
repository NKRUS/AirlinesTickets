package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.DBUtil;

import java.sql.*;

/**
 * Created by NK on 21.11.2016.
 */
public class FlightDAO {
    public static ObservableList<FlightSearchResult> searchFlights(City from, City whereTo, Date date, PlaceClass placeClass) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM flights f\n" +
                "INNER JOIN prices_and_places pap\n" +
                "ON f.id = pap.flight_id AND pap.class_id = "+placeClass.getClass_id()+" AND pap.free_places_count>0\n"+
                "WHERE f.departure_date = '"+date+"' AND f.departure_city_id = "+from.getCity_id()+" AND f.arrival_city_id = "+whereTo.getCity_id()+"\n"+
                "ORDER BY f.departure_time ASC;";


        try {
            ResultSet rsCts = DBUtil.dbExecuteQuery(selectStmt);
            return getFlightSearchResultList(rsCts);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<FlightSearchResult> getFlightSearchResultList(ResultSet rsCts) throws SQLException {
        ObservableList<FlightSearchResult> fsrl = FXCollections.observableArrayList();

        while (rsCts.next()){
            FlightSearchResult flightSearchResult = new FlightSearchResult();
            Flight flight = new Flight();
            PriceAndPlace priceAndPlace = new PriceAndPlace();
            flight.setFlight_id(rsCts.getInt("id"));
            flight.setFlight_number(rsCts.getString("flight_number"));
            flight.setAirlines_id(rsCts.getInt("airlines_id"));
            flight.setDeparture_city_id(rsCts.getInt("departure_city_id"));
            flight.setArrival_city_id(rsCts.getInt("arrival_city_id"));
            flight.setDeparture_date((Date) rsCts.getObject("departure_date"));
            flight.setDeparture_time((Time) rsCts.getObject("departure_time"));
            flight.setArrival_date((Date) rsCts.getObject("arrival_date"));
            flight.setArrival_time((Time) rsCts.getObject("arrival_time"));
            priceAndPlace.setFlight_id(rsCts.getInt("flight_id"));
            priceAndPlace.setClass_id(rsCts.getInt("class_id"));
            priceAndPlace.setPrice(rsCts.getDouble("price"));
            priceAndPlace.setFree_places_count(rsCts.getInt("free_places_count"));
            flightSearchResult.setFlight(flight);
            flightSearchResult.setPrice_and_place(priceAndPlace);
            fsrl.add(flightSearchResult);
        }
        return fsrl;
    }
}
