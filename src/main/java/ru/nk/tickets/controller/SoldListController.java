package ru.nk.tickets.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.nk.tickets.dao.AirlineDAO;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.OrderDAO;
import ru.nk.tickets.dao.PlaceClassDAO;
import ru.nk.tickets.model.SoldTickets;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class SoldListController {

    public static ObservableList<SoldTickets> soldTickets = FXCollections.observableArrayList();
    @FXML
    private TableView<SoldTickets> soldSeatsTableView;
    @FXML
    private TableColumn<SoldTickets,String> flightTableColumn, soldTicketsTableColumn,
    freeSeatsTableColumn, seatClassTableColumn;


    @FXML
    private void initialize() {
        try {
            soldTickets = OrderDAO.getSoldTickets();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Util.showAlert(Alert.AlertType.WARNING,"Ошибка",null,"Возникла проблема при считывании списка проданных мест");
        }
        initTable();
    }

    private void initTable() {
        soldSeatsTableView.setItems(soldTickets);
        flightTableColumn.setCellValueFactory(param -> {
            String s = "";
            try {
                s = param.getValue().getFlight().getFlight_number()+" "+
                        AirlineDAO.getAirlineById((param.getValue().getFlight().getAirlines_id())).getName()+" Откуда: "+
                        CityDAO.getCityById(param.getValue().getFlight().getDeparture_city_id()).getCity()+" Куда: "+
                        CityDAO.getCityById(param.getValue().getFlight().getArrival_city_id()).getCity()+" Когда: "+
                        Util.dateToTicketString(param.getValue().getFlight().getDeparture_date())+" - "+
                        Util.timeToString(param.getValue().getFlight().getDeparture_time());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return new ReadOnlyStringWrapper(s);
        });
        soldTicketsTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getSold_tickets())));
        freeSeatsTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getFree_places_count())));
        seatClassTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(PlaceClassDAO.getPlaceClassById(param.getValue().getClass_id()).getClass_title());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });

    }
}
