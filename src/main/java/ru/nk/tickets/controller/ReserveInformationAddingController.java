package ru.nk.tickets.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import ru.nk.tickets.Main;
import ru.nk.tickets.dao.*;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by NK on 22.11.2016.
 */
public class ReserveInformationAddingController {

    private ObservableList<Document> documents;
    public static ArrayList<Order> orderArrayList = new ArrayList<>();
    public static ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

    @FXML
    private TextField surnameTextField, nameTextField, documentNumberTextField, organizationTextField,
            phoneTextField, emailTextField;
    @FXML
    private ComboBox<Document> documentTypeComboBox;
    @FXML
    private TableView reserveTable;
    @FXML
    private TableColumn<FlightSearchResult, String> flightNumberColumn, fromCityColumn, toCityColumn, departureDateTimeColumn,
            arrivalDateTimeColumn, serviceClassColumn, aviacompanyColumn, countColumn;
    @FXML
    private TableColumn<FlightSearchResult, Double> priceColumn;
    @FXML
    private Button acceptButton;


    @FXML
    private void initialize() {
        reserveTable.setItems(ReserveController.preReserve);
        initUI();
        initTableView();
    }

    private void initUI() {
        //Combobox - init----
        if (documents == null) try {
            documents = DocumentDAO.getDocuments();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        documentTypeComboBox.setItems(documents);
        documentTypeComboBox.getSelectionModel().selectFirst();
        //---

    }

    private void initTableView() {
        flightNumberColumn.setEditable(false);
        flightNumberColumn.setCellValueFactory(param -> param.getValue().getFlight().flight_numberProperty());
        fromCityColumn.setEditable(false);
        fromCityColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getFlight().getDeparture_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        toCityColumn.setEditable(false);
        toCityColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getFlight().getArrival_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        departureDateTimeColumn.setEditable(false);
        departureDateTimeColumn.setCellValueFactory(param -> Bindings.concat(
                new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getFlight().getDeparture_date())),
                " ",
                new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getFlight().getDeparture_time()))
        ));
        arrivalDateTimeColumn.setEditable(false);
        arrivalDateTimeColumn.setCellValueFactory(param -> Bindings.concat(
                new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getFlight().getArrival_date())),
                " ",
                new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getFlight().getArrival_time()))
        ));
        serviceClassColumn.setEditable(false);
        serviceClassColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(PlaceClassDAO.getPlaceClassById(param.getValue().getPrice_and_place().getClass_id()).getClass_title());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        aviacompanyColumn.setEditable(false);
        aviacompanyColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(AirlineDAO.getAirlineById(param.getValue().getFlight().getAirlines_id()).getName());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });

        priceColumn.setEditable(false);
        priceColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrice_and_place().getPrice()));
        countColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper("1"));

    }

    @FXML
    private void acceptOrder() {
        orderArrayList.clear();
        orderDetailsArrayList.clear();
        if (beforeAcceptCheck()) {
            int counter = -1;
            for (FlightSearchResult flightSearchResult : ReserveController.preReserve) {
                counter++;
                Order order = new Order();
                order.setDocument_id(documentTypeComboBox.getValue().getDocument_id());
                order.setDocument_number(documentNumberTextField.getText());
                order.setName(nameTextField.getText());
                order.setSurname(surnameTextField.getText());
                order.setEmail(emailTextField.getText().isEmpty() ? null : emailTextField.getText());
                order.setPhone_number(phoneTextField.getText());
                order.setOrganization_name(organizationTextField.getText().isEmpty() ? null : organizationTextField.getText());
                order.setPayed(false);

                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setFlight_id(flightSearchResult.getFlight().getFlight_id());
                try {
                    orderDetails.setOrder_id(OrderDAO.getLastOrderId() + 1);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                orderDetails.setClass_id(flightSearchResult.getPrice_and_place().getClass_id());
                orderDetails.setNumber_of_tickets(1);

                try {
                    OrderDAO.acceptReserve(order, orderDetails);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Util.showAlert(Alert.AlertType.ERROR, "Ошибка", null, "Не удалось занести информацию о заказе в базу данных");
                    return;
                }
                ReserveInformationAddingController.orderArrayList.add(order);
                ReserveInformationAddingController.orderDetailsArrayList.add(orderDetails);
            }
            //Util.showAlert(Alert.AlertType.INFORMATION,"Успешно",null,"Информация о заказе занесена в базу данных");
            Main.controller.currentScreen = Main.RESERVE_RESULT;
            Main.controller.myController.loadScreen(Main.RESERVE_RESULT,
                    Main.RESERVE_RESULT_FXML);
            Main.controller.myController.setScreen(Main.RESERVE_RESULT);
        }
    }

    private boolean beforeAcceptCheck() {
        if (surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty()
                || documentNumberTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание", null, "Пожалуйста заполните обязательные поля!");
            return false;
        }
        if (!Util.validateOnlyNumbers(documentNumberTextField.getText())) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание", null, "Поле Номер документа может содержать только цифры и пробелы!");
            documentNumberTextField.requestFocus();
            return false;
        }

        if (!phoneTextField.getText().isEmpty() && !Util.validateOnlyNumbers(phoneTextField.getText())) {
            Util.showAlert(Alert.AlertType.WARNING, "Внимание", null, "Поле Номер телефона* может содержать только цифры и пробелы!");
            phoneTextField.requestFocus();
            return false;
        }

        return true;
    }


}
