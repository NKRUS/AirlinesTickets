package ru.nk.tickets.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.PlaceClass;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 21.11.2016.
 */
public class PlaceClassDAO {
    public static ObservableList<PlaceClass> getPlaceClasses() throws ClassNotFoundException, SQLException {
        String selectStmt = "SELECT * FROM place_classes";
        try {
            ResultSet rsCts = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<PlaceClass> placeClasses = getPlaceClassesList(rsCts);
            return placeClasses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<PlaceClass> getPlaceClassesList(ResultSet rsCts) throws SQLException {
        ObservableList<PlaceClass> placeClasses  = FXCollections.observableArrayList();

        while (rsCts.next()){
            PlaceClass placeClass = new PlaceClass();
            placeClass.setClass_id(rsCts.getInt("id"));
            placeClass.setClass_title(rsCts.getString("class"));
            placeClasses.add(placeClass);
        }
        return placeClasses;
    }
}
