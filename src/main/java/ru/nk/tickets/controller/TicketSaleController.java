package ru.nk.tickets.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import ru.nk.tickets.Main;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.FlightDAO;
import ru.nk.tickets.dao.PlaceClassDAO;
import ru.nk.tickets.model.City;
import ru.nk.tickets.model.FlightSearchResult;
import ru.nk.tickets.model.PlaceClass;
import ru.nk.tickets.util.Util;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * Created by NK on 14.11.2016.
 */
public class TicketSaleController implements SearchableTicketScreen {
    final ToggleGroup group = new ToggleGroup();
    private ObservableList<City> cities;
    public static ObservableList<FlightSearchResult> preOrder = FXCollections.observableArrayList();
    public static boolean isOneWay = true;
    ObservableList<FlightSearchResult> listForward = null;
    ObservableList<FlightSearchResult> listBackward = null;

    @FXML
    private RadioButton oneWay, bothWays;
    @FXML
    private DatePicker forwardDate,backwardDate;
    @FXML
    private TextField from, whereTo;
    @FXML
    private ComboBox<PlaceClass> serviceClass;
    @FXML
    private VBox resultsTab;
    @FXML
    private Text titleOfResult;

    ScreensController myController;

    @FXML
    private void initialize() {
        initUI();
    }

    @Override
    public void addToOrder(FlightSearchResult flightSearchResult){
        preOrder.add(flightSearchResult);

        if((isOneWay && preOrder.size()==1)||(!isOneWay && preOrder.size()==2)){
                MakeOrderController.isBuyingAfterReserve = false;
                Main.controller.currentScreen = Main.MAKE_ORDER;
                Main.controller.myController.loadScreen(Main.MAKE_ORDER,
                        Main.MAKE_ORDER_FXML);
                Main.controller.myController.setScreen(Main.MAKE_ORDER);
        }

        if (!isOneWay && preOrder.size()==1){
            titleOfResult.setText("Выберите билет - обратно");
            fillSearchResult(listBackward);
        }
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void searchTicket(){
        if(beforeSearchCheck()){
            //Подготовка
            titleOfResult.setText("Выберите билет - туда");
            if (listForward!=null) listForward.clear();
            if (listBackward!=null) listBackward.clear();
            preOrder.clear();
            isOneWay = oneWay.isSelected();
            //Конец подготовки

            City fromCity = Util.getCityByName(cities, from.getText());
            City toCity = Util.getCityByName(cities, whereTo.getText());

            java.sql.Date dateSQL = Util.localDateToSqlDate(forwardDate.getValue());

            java.sql.Date dateBSQL = null;
            if(bothWays.isSelected()){
                dateBSQL = Util.localDateToSqlDate(backwardDate.getValue());
            }

            PlaceClass pc = serviceClass.getSelectionModel().getSelectedItem();

            try {
               listForward = searchInDB(fromCity,toCity,dateSQL,pc);
               if(bothWays.isSelected()) listBackward = searchInDB(toCity,fromCity,dateBSQL,pc);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            assert listForward != null;
            if(listForward.size()!=0){
                fillSearchResult(listForward);
            }else {
                Util.showAlert(Alert.AlertType.INFORMATION,"Не найдено",null,"По вашему запросу ничего не найдено");
            }


        }

    }

    private ObservableList<FlightSearchResult> searchInDB(City from, City where, Date when, PlaceClass pc) throws SQLException, ClassNotFoundException {
        ObservableList<FlightSearchResult> result = null;
        try {
            return FlightDAO.searchFlights(from,where,when,pc);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }
    private void fillSearchResult(ObservableList<FlightSearchResult> r){
        resultsTab.getChildren().clear();
        for (FlightSearchResult flightSearchResult : r) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Main.SEARCHED_TICKET_BLOCK_FXML));
            try {
                AnchorPane p = fxmlLoader.load();
                SearchedTicketBlockController stbc = fxmlLoader.getController();
                stbc.setParentController(this);
                stbc.populateValues(flightSearchResult);
                //
                resultsTab.getChildren().add(p);
                VBox.setMargin(p,new Insets(10,0,0,0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private boolean beforeSearchCheck(){
        resultsTab.getChildren().clear();
        if(from.getText().isEmpty() || whereTo.getText().isEmpty()
                || serviceClass.getSelectionModel().isEmpty() || forwardDate.getValue()==null
                || (bothWays.isSelected() && backwardDate.getValue() == null)){

            Util.showAlert(Alert.AlertType.WARNING,"Внимание!",null,"Заполните все поля!");
            return false;
        }
        if(!Util.containsCity(cities,from.getText()) || !Util.containsCity(cities,whereTo.getText())){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание!",null,"Таких городов нет в базе данных!");
            return false;
        }
        if(bothWays.isSelected() && (forwardDate.getValue().compareTo(backwardDate.getValue())>=0)){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание!",null,"Обратная дата не может быть раньше даты вылета!");
            return false;
        }

        return true;
    }



    @FXML
    private void goToMain(ActionEvent event) {
        myController.setScreen(Main.MAIN_SCREEN);
    }

    //any required method here
}