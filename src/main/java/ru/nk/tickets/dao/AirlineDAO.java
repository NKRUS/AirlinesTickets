package ru.nk.tickets.dao;

import ru.nk.tickets.model.Airline;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
