package ru.nk.tickets.dao;

import ru.nk.tickets.model.PriceAndPlace;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 11.12.2016.
 */
public class PriceAndPlaceDAO {
    private static PriceAndPlace getPriceAndPlaceFromResultSet(ResultSet rs) throws SQLException {
        PriceAndPlace priceAndPlace = null;
        if (rs.next()){
            priceAndPlace = new PriceAndPlace();
            priceAndPlace.setFlight_id(rs.getInt("flight_id"));
            priceAndPlace.setClass_id(rs.getInt("class_id"));
            priceAndPlace.setPrice(rs.getDouble("price"));
            priceAndPlace.setFree_places_count(rs.getInt("free_places_count"));
        }
        return priceAndPlace;
    }

    public static PriceAndPlace getPriceAndPlaceById(int flight_id, int class_id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM prices_and_places WHERE flight_id="+flight_id+" AND class_id="+class_id+";";

        try{
            ResultSet rsPriceAndPlace = DBUtil.dbExecuteQuery(selectStmt);
            PriceAndPlace city = getPriceAndPlaceFromResultSet(rsPriceAndPlace);
            return city;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
