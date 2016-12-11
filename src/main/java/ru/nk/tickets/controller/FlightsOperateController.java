package ru.nk.tickets.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import ru.nk.tickets.dao.AirlineDAO;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.FlightDAO;
import ru.nk.tickets.model.Airline;
import ru.nk.tickets.model.City;
import ru.nk.tickets.model.Flight;
import ru.nk.tickets.model.PriceAndPlace;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

/**
 * Created by NK on 27.11.2016.
 */
public class FlightsOperateController {
    ObservableList<Flight> flights = FXCollections.observableArrayList();
    private ObservableList<City> cities;
    @FXML
    private TableView<Flight> flightsTableView;

    @FXML
    private TableColumn<Flight, String> idTableColumn, flightNumberTableColumn, airlineTableColumn, departureCityTableColumn,
            arrivalCityTableColumn, departureDateTableColumn, departureTimeTableColumn, arrivalDateTableColumn, arrivalTimeTableColumn;

    @FXML
    private TextField flightNumberTextField, departureCityTextField, arrivalCityTextField, departureTimeTextField, arrivalTimeTextField,
            economPlacesTextField, buissnesPlacesTextField, vipPlacesTextField, economPriceTextField, buissnesPriceTextField, vipPriceTextField;
    @FXML
    private ComboBox<Airline> airlineComboBox;
    @FXML
    private DatePicker departureDateDatePicker, arrivalDateDatePicker;


    @FXML
    private void initialize() {
        refreshFlightsData();
        initTable();
        initAddUI();
    }

    private void initAddUI() {
        if (cities == null) try {
            cities = CityDAO.getCities();
            TextFields.bindAutoCompletion(departureCityTextField, cities);
            TextFields.bindAutoCompletion(arrivalCityTextField, cities);
            airlineComboBox.setItems(AirlineDAO.getAirlines());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearTextFields();
    }

    @FXML
    private void refreshFlightsTable(){
        refreshFlightsData();
        initTable();
    }
    private void refreshFlightsData(){
        try {
            flights = FlightDAO.getFlights();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Util.showAlert(Alert.AlertType.WARNING, "Ошибка", null, "Возникла проблема при считывании списка рейсов");
        }
    }

    private void initTable() {
        flightsTableView.setItems(flights);

        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getFlight_id())));
        flightNumberTableColumn.setCellValueFactory(param -> param.getValue().flight_numberProperty());
        airlineTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(AirlineDAO.getAirlineById(param.getValue().getAirlines_id()).getName());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        departureCityTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getDeparture_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        arrivalCityTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getArrival_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        departureDateTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getDeparture_date())));

        departureTimeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getDeparture_time())));
        arrivalDateTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getArrival_date())));
        arrivalTimeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getArrival_time())));
    }

    @SuppressWarnings("ConstantConditions")
    @FXML
    private void addFlight() {
        if (beforeCheck()) {
            Flight flight = new Flight();
            flight.setFlight_number(flightNumberTextField.getText());
            flight.setAirlines_id(airlineComboBox.getSelectionModel().getSelectedItem().getAirline_id());
            flight.setDeparture_city_id(Util.getCityByName(cities, departureCityTextField.getText()).getCity_id());
            flight.setArrival_city_id(Util.getCityByName(cities, arrivalCityTextField.getText()).getCity_id());

            flight.setDeparture_date(Util.localDateToSqlDate(departureDateDatePicker.getValue()));
            flight.setArrival_date(Util.localDateToSqlDate(arrivalDateDatePicker.getValue()));

            flight.setDeparture_time(Time.valueOf(departureTimeTextField.getText() + ":00"));
            flight.setArrival_time(Time.valueOf(arrivalTimeTextField.getText() + ":00"));

            try {
                FlightDAO.insertFlight(flight);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                Util.showAlert(Alert.AlertType.ERROR, "Ошибка!", null, "Не удалось добавить рейс в базу данных!");
                return;
            }
            ObservableList<PriceAndPlace> papList = preprocessPriceAndPlaces();

            for (PriceAndPlace priceAndPlace :papList) {
                try {
                    FlightDAO.insertPriceAndPlace(priceAndPlace);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Util.showAlert(Alert.AlertType.WARNING, "Ошибка!", null, "Не удалось добавить информацию о классах обслуживания и стоимости в базу данных");
                    return;
                }
            }
            Util.showAlert(Alert.AlertType.INFORMATION, "Успешно!", null, "Информация о рейсе успешно занесена в базу");
            clearTextFields();

        }
    }

    private ObservableList<PriceAndPlace> preprocessPriceAndPlaces(){
        int maxID = 0;
        try {
            maxID = FlightDAO.getLastAddedFlightID();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<PriceAndPlace> papList = FXCollections.observableArrayList();
        if (!((economPlacesTextField.getText().isEmpty() || economPlacesTextField.getText().equals("0")) && (economPriceTextField.getText().isEmpty() || economPriceTextField.getText().equals("0")))){
            PriceAndPlace pap = new PriceAndPlace();
            pap.setFlight_id(maxID);
            pap.setClass_id(1);
            System.out.println(Integer.parseInt(economPlacesTextField.getText()));
            System.out.println(Double.parseDouble(economPriceTextField.getText()));
            pap.setFree_places_count(Integer.parseInt(economPlacesTextField.getText()));
            pap.setPrice(Double.parseDouble(economPriceTextField.getText()));
            papList.add(pap);
        }
        if (!((buissnesPlacesTextField.getText().isEmpty() || buissnesPlacesTextField.getText().equals("0")) && (buissnesPriceTextField.getText().isEmpty() || buissnesPriceTextField.getText().equals("0")))){
            PriceAndPlace pap = new PriceAndPlace();
            pap.setFlight_id(maxID);
            pap.setClass_id(2);
            pap.setFree_places_count(Integer.parseInt(buissnesPlacesTextField.getText()));
            pap.setPrice(Double.parseDouble(buissnesPriceTextField.getText()));
            papList.add(pap);
        }
        if (!((vipPlacesTextField.getText().isEmpty() || vipPlacesTextField.getText().equals("0")) && (vipPriceTextField.getText().isEmpty() || vipPriceTextField.getText().equals("0")))){
            PriceAndPlace pap = new PriceAndPlace();
            pap.setFlight_id(maxID);
            pap.setClass_id(3);
            pap.setFree_places_count(Integer.parseInt(vipPlacesTextField.getText()));
            pap.setPrice(Double.parseDouble(vipPriceTextField.getText()));
            papList.add(pap);
        }

        return papList;

    }

    private void clearTextFields(){
        flightNumberTextField.setText("");
        departureCityTextField.setText("");
        arrivalCityTextField.setText("");
        departureTimeTextField.setText("");
        arrivalTimeTextField.setText("");
        economPlacesTextField.setText("0");
        buissnesPlacesTextField.setText("0");
        vipPlacesTextField.setText("0");
        economPriceTextField.setText("0");
        buissnesPriceTextField.setText("0");
        vipPriceTextField.setText("0");
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
        departureDateDatePicker.setDayCellFactory(dayCellFactory);
        arrivalDateDatePicker.setDayCellFactory(dayCellFactory);
        departureDateDatePicker.setValue(LocalDate.now());
        arrivalDateDatePicker.setValue(LocalDate.now());

        airlineComboBox.getSelectionModel().selectFirst();
    }

    private boolean beforeCheck() {
        if (flightNumberTextField.getText().isEmpty() || departureCityTextField.getText().isEmpty() || arrivalCityTextField.getText().isEmpty()
                || departureDateDatePicker.getValue() == null || departureTimeTextField.getText().isEmpty()
                || arrivalDateDatePicker.getValue() == null || arrivalTimeTextField.getText().isEmpty()
                || economPlacesTextField.getText().isEmpty() || buissnesPlacesTextField.getText().isEmpty() || vipPlacesTextField.getText().isEmpty()) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание!", null, "Заполните все поля!");
            return false;
        }
        if (!Util.containsCity(cities, departureCityTextField.getText()) || !Util.containsCity(cities, arrivalCityTextField.getText())) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание!", null, "Таких городов нет в базе данных!");
            return false;
        }
        if ((departureDateDatePicker.getValue().compareTo(arrivalDateDatePicker.getValue()) > 0)) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание!", null, "Дата прилета не может быть раньше даты вылета!");
            return false;
        }
        //Проверки цен и кол-ва мест
        if (!((economPlacesTextField.getText().isEmpty() || economPlacesTextField.getText().equals("0")) && (economPriceTextField.getText().isEmpty() || economPriceTextField.getText().equals("0")))
                || !((buissnesPlacesTextField.getText().isEmpty() || buissnesPlacesTextField.getText().equals("0")) && (buissnesPriceTextField.getText().isEmpty() || buissnesPriceTextField.getText().equals("0")))
                || !((vipPlacesTextField.getText().isEmpty() || vipPlacesTextField.getText().equals("0")) && (vipPriceTextField.getText().isEmpty() || vipPriceTextField.getText().equals("0")))) {

        }else {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание!", null, "Хотя бы в одном из классов должны быть укзаны места со стоимостью");
        }
        if (!Util.validateOnlyNumbers(economPlacesTextField.getText()) || !Util.validateOnlyNumbers(buissnesPlacesTextField.getText())
                || !Util.validateOnlyNumbers(vipPlacesTextField.getText()) || !Util.validateOnlyNumbers(economPriceTextField.getText())
                || !Util.validateOnlyNumbers(buissnesPriceTextField.getText()) || !Util.validateOnlyNumbers(vipPriceTextField.getText())) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание!", null, "Поля кол-ва мест и цен могут содержать только цифры");
        }

        return true;
    }


}
