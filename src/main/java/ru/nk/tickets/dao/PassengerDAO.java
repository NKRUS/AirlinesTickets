package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.Passenger;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class PassengerDAO {
    public static ObservableList<Passenger> getPassengersFromArchive() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM passengers_archive";

        try {
            ResultSet rsPas = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<Passenger> passengersArchiveList = getPassengersList(rsPas);
            return passengersArchiveList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<Passenger> getPassengersList(ResultSet rs) throws SQLException {
        ObservableList<Passenger> passengerList = FXCollections.observableArrayList();

        while (rs.next()){
            Passenger passenger = new Passenger();
            passenger.setPassenger_id(rs.getInt("id"));
            passenger.setOrder_id(rs.getInt("order_id"));
            passenger.setFlight_id(rs.getInt("flight_id"));
            passenger.setClass_id(rs.getInt("class_id"));
            passenger.setSurname(rs.getString("surname"));
            passenger.setName(rs.getString("name"));
            passenger.setPatronymic(rs.getString("patronymic"));
            passenger.setIsMan(rs.getBoolean("gender"));
            passenger.setDate_of_birth(rs.getDate("date_of_birth"));
            passenger.setDocument_id(rs.getInt("document_id"));
            passenger.setDocument_number(rs.getString("document_number"));
            passengerList.add(passenger);
        }
        return passengerList;
    }
}
