package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.ArchivePassenger;
import ru.nk.tickets.model.Passenger;
import ru.nk.tickets.util.DBUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class PassengerDAO {
    public static ObservableList<ArchivePassenger> getPassengersFromArchive() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM passengers_archive";

        try {
            ResultSet rsPas = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<ArchivePassenger> passengersArchiveList = getPassengersList(rsPas);
            return passengersArchiveList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<ArchivePassenger> getPassengersList(ResultSet rs) throws SQLException {
        ObservableList<ArchivePassenger> passengerList = FXCollections.observableArrayList();

        while (rs.next()){
            ArchivePassenger archivePassenger = new ArchivePassenger();
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
            archivePassenger.setPassenger(passenger);
            archivePassenger.setFlight_number(rs.getString("flight_number"));
            archivePassenger.setAirline(rs.getString("airline"));
            archivePassenger.setDeparture_city(rs.getString("departure_city"));
            archivePassenger.setArrival_city(rs.getString("arrival_city"));
            archivePassenger.setDeparture_date((Date) rs.getObject("departure_date"));


            passengerList.add(archivePassenger);
        }
        return passengerList;
    }
}
