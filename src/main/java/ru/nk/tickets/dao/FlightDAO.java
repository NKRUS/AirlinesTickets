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
    public static ObservableList<FlightSearchResult> searchFlights(City from, City whereTo, Date date, PlaceClass placeClass) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM flights f\n" +
                "INNER JOIN prices_and_places pap\n" +
                "ON f.id = pap.flight_id AND pap.class_id = " + placeClass.getClass_id() + " AND pap.free_places_count>0\n" +
                "WHERE f.departure_date = '" + date + "' AND f.departure_city_id = " + from.getCity_id() + " AND f.arrival_city_id = " + whereTo.getCity_id() + "\n" +
                "ORDER BY f.departure_time ASC;";


        try {
            ResultSet rsCts = DBUtil.dbExecuteQuery(selectStmt);
            return getFlightSearchResultList(rsCts);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ObservableList<Flight> getFlights() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM flights ORDER BY id;";
        try {
            ResultSet rsFlights = DBUtil.dbExecuteQuery(selectStmt);
            return getFlightList(rsFlights);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static boolean isFlightExists(String flightNumber) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM flights WHERE flight_number='"+flightNumber+"';";
        try {
            ResultSet rsFlights = DBUtil.dbExecuteQuery(selectStmt);
            if(getFlightList(rsFlights).size()!=0) return true;
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void insertFlight(Flight flight) throws SQLException, ClassNotFoundException {
        String insertStmt = "INSERT INTO flights (flight_number, airlines_id, departure_city_id, arrival_city_id, departure_date, departure_time, arrival_date, arrival_time) \n" +
                "VALUES ('" + flight.getFlight_number() + "', " + flight.getAirlines_id() + ", " + flight.getDeparture_city_id() + "," +
                " " + flight.getArrival_city_id() + ", '" + flight.getDeparture_date() + "', '" + flight.getDeparture_time() + "', '" + flight.getArrival_date() + "'," +
                " '" + flight.getArrival_time() + "');";

        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }
    public static void insertPriceAndPlace(PriceAndPlace priceAndPlace) throws SQLException, ClassNotFoundException {
        String insertStmt = "INSERT INTO prices_and_places (flight_id, class_id, price, free_places_count) \n" +
                "VALUES ("+priceAndPlace.getFlight_id()+", "+priceAndPlace.getClass_id()+", "+priceAndPlace.getPrice()+", "+priceAndPlace.getFree_places_count()+");";
        try {
            DBUtil.dbExecuteUpdate(insertStmt);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static int getLastAddedFlightID() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT MAX(id) AS id FROM flights;";
        try {
            ResultSet rsFlight = DBUtil.dbExecuteQuery(selectStmt);
            int max = 0;
            if(rsFlight.next()){
                max = rsFlight.getInt("id");
            }
            return max;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static ObservableList<Flight> getFlightList(ResultSet resultSet) throws SQLException {
        ObservableList<Flight> frl = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Flight flight = new Flight();
            flight.setFlight_id(resultSet.getInt("id"));
            flight.setFlight_number(resultSet.getString("flight_number"));
            flight.setAirlines_id(resultSet.getInt("airlines_id"));
            flight.setDeparture_city_id(resultSet.getInt("departure_city_id"));
            flight.setArrival_city_id(resultSet.getInt("arrival_city_id"));
            flight.setDeparture_date((Date) resultSet.getObject("departure_date"));
            flight.setDeparture_time((Time) resultSet.getObject("departure_time"));
            flight.setArrival_date((Date) resultSet.getObject("arrival_date"));
            flight.setArrival_time((Time) resultSet.getObject("arrival_time"));
            frl.add(flight);
        }
        return frl;
    }

    private static ObservableList<FlightSearchResult> getFlightSearchResultList(ResultSet rsCts) throws SQLException {
        ObservableList<FlightSearchResult> fsrl = FXCollections.observableArrayList();

        while (rsCts.next()) {
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

    public static Flight getFlightById(int id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM flights WHERE id="+id;

        try{
            ResultSet rsFlight = DBUtil.dbExecuteQuery(selectStmt);
            Flight flight = getFlightFromResultSet(rsFlight);
            return flight;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
    private static Flight getFlightFromResultSet(ResultSet rs) throws SQLException {
        Flight flight = null;
        if (rs.next()){
            flight = new Flight();
            flight.setFlight_id(rs.getInt("id"));
            flight.setFlight_number(rs.getString("flight_number"));
            flight.setAirlines_id(rs.getInt("airlines_id"));
            flight.setDeparture_city_id(rs.getInt("departure_city_id"));
            flight.setArrival_city_id(rs.getInt("arrival_city_id"));
            flight.setDeparture_date((Date) rs.getObject("departure_date"));
            flight.setDeparture_time((Time) rs.getObject("departure_time"));
            flight.setArrival_date((Date) rs.getObject("arrival_date"));
            flight.setArrival_time((Time) rs.getObject("arrival_time"));
        }
        return flight;
    }
}
