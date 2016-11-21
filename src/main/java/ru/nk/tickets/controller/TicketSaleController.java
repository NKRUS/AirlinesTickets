package ru.nk.tickets.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationSupport;
import ru.nk.tickets.Main;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.FlightDAO;
import ru.nk.tickets.dao.PlaceClassDAO;
import ru.nk.tickets.model.City;
import ru.nk.tickets.model.FlightSearchResult;
import ru.nk.tickets.model.PlaceClass;
import ru.nk.tickets.util.Util;

import javax.xml.validation.Validator;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by NK on 14.11.2016.
 */
public class TicketSaleController {
    final ToggleGroup group = new ToggleGroup();
    private ObservableList<City> cities;

    @FXML
    private RadioButton oneWay, bothWays;
    @FXML
    private DatePicker forwardDate,backwardDate;
    @FXML
    private TextField from, whereTo;
    @FXML
    private ComboBox<PlaceClass> serviceClass;

    ScreensController myController;

    @FXML
    public void initialize() {
        initUI();
    }

    private void initUI() {
        oneWay.setToggleGroup(group);
        oneWay.setSelected(true);
        bothWays.setToggleGroup(group);
        backwardDate.setDisable(true);
        forwardDate.setValue(LocalDate.now());
        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusYears(1)))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    Platform.runLater(() -> setDisable(true));

                }
            }
        };
        forwardDate.setDayCellFactory(dayCellFactory);
        backwardDate.setDayCellFactory(dayCellFactory);
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            RadioButton b = (RadioButton) group.getSelectedToggle();
            if(b.getId().equals("oneWay")){
                backwardDate.setValue(null);
                backwardDate.setDisable(true);
            }else if(b.getId().equals("bothWays")){
                backwardDate.setDisable(false);
            }
        });
        try {
            if(cities==null) cities = CityDAO.getCities();
            TextFields.bindAutoCompletion(from, cities);
            TextFields.bindAutoCompletion(whereTo, cities);
            serviceClass.setItems(PlaceClassDAO.getPlaceClasses());
            serviceClass.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchTicket(){
        if(beforeSearchCheck()){
            City fromCity = Util.getCityByName(cities, from.getText());
            City toCity = Util.getCityByName(cities, whereTo.getText());
            java.util.Date f_date =
                    java.util.Date.from(forwardDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateSQL = new java.sql.Date(f_date.getTime());

            PlaceClass pc = serviceClass.getSelectionModel().getSelectedItem();

            /*java.util.Date b_date =
                    java.util.Date.from(backwardDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date bacwardSQLDate = new java.sql.Date(b_date.getTime());*/

            try {
                System.out.println(FlightDAO.searchFlights(fromCity,toCity,dateSQL,pc));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private void fillSearchResult(){

    }
    private boolean beforeSearchCheck(){
        if(from.getText().isEmpty() || whereTo.getText().isEmpty()
                || serviceClass.getSelectionModel().isEmpty() || forwardDate.getValue()==null
                || (bothWays.isSelected() && backwardDate.getValue() == null)){

            showAlert(Alert.AlertType.WARNING,"Внимание!",null,"Заполните все поля!");
            return false;
        }
        if(!Util.containsCity(cities,from.getText()) || !Util.containsCity(cities,whereTo.getText())){
            showAlert(Alert.AlertType.WARNING,"Внимание!",null,"Таких городов нет в базе данных!");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String headerText, String contextText){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);

        alert.showAndWait();
    }

    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(Main.MAIN_SCREEN);
    }

    //any required method here
}