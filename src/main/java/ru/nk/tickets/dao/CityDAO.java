package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.City;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by NK on 21.11.2016.
 */
public class CityDAO {
    public static ObservableList<City> getCities() throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM cities";

        try {
            ResultSet rsCts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<City> cityList = getCityList(rsCts);
            return cityList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<City> getCityList(ResultSet rs) throws SQLException {
        ObservableList<City> cityList = FXCollections.observableArrayList();

        while (rs.next()){
            City city = new City();
            city.setCity_id(rs.getInt("id"));
            city.setCity(rs.getString("city"));
            cityList.add(city);
        }
        return cityList;
    }

    private static City getCityFromResultSet(ResultSet rs) throws SQLException {
        City city = null;
        if (rs.next()){
            city = new City();
            city.setCity_id(rs.getInt("id"));
            city.setCity(rs.getString("city"));
        }
        return city;
    }

    public static City getCityById(int id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM cities WHERE id="+id;

        try{
            ResultSet rsCity = DBUtil.dbExecuteQuery(selectStmt);
            City city = getCityFromResultSet(rsCity);
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
