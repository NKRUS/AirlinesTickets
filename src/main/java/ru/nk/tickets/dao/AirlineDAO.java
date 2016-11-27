package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.Airline;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Created by NK on 22.11.2016.
 */
public class AirlineDAO {
    private static Airline getAirlineFromResultSet(ResultSet rs) throws SQLException {
        Airline airline = null;
        if (rs.next()){
            airline = new Airline();
            airline.setAirline_id(rs.getInt("id"));
            airline.setName(rs.getString("name"));
            airline.setShort_name(rs.getString("short_title"));
        }
        return airline;
    }

    public static Airline getAirlineById(int id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM airlines WHERE id="+id;

        try{
            ResultSet rsAirlines = DBUtil.dbExecuteQuery(selectStmt);
            Airline airline = getAirlineFromResultSet(rsAirlines);
            return airline;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ObservableList<Airline> getAirlines() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM airlines;";

        try {
            ResultSet rsA = DBUtil.dbExecuteQuery(selectStmt);
            return getAirlinesFromResultSet(rsA);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Airline> getAirlinesFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<Airline> airlineObservableListA = FXCollections.observableArrayList();

        while (resultSet.next()){
            Airline airline = new Airline();
            airline.setAirline_id(resultSet.getInt("id"));
            airline.setName(resultSet.getString("name"));
            airline.setShort_name(resultSet.getString("short_title"));
            airlineObservableListA.add(airline);
        }
        return airlineObservableListA;
    }

    public static void main(String[] args) {
        Time.valueOf("12:30");
    }
}
