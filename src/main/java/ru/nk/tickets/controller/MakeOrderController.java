package ru.nk.tickets.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.jsolve.sweetener.criteria.restriction.Or;
import ru.nk.tickets.Main;
import ru.nk.tickets.dao.*;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by NK on 22.11.2016.
 */
public class MakeOrderController {

    private ObservableList<FlightSearchResult> preOrder = FXCollections.observableArrayList();
    private ObservableList<Document> documents;
    public static ArrayList<Passenger> passengerArrayList  = new ArrayList<>();
    public static ArrayList<Order> orderArrayList = new ArrayList<>();
    public static ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();
    public static boolean isBuyingAfterReserve = false;

    @FXML
    private TextField surnameTextField, nameTextField, patronymicTextField, documentNumberTextField, organizationTextField,
            phoneTextField, emailTextField;
    @FXML
    private ComboBox<Document> documentTypeComboBox;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private DatePicker dateOfBirthDatPicker;
    @FXML
    private TableView<FlightSearchResult> orderTable;
    @FXML
    private TableColumn<FlightSearchResult, String> flightNumberColumn, fromCityColumn, toCityColumn, departureDateTimeColumn,
            arrivalDateTimeColumn, serviceClassColumn, aviacompanyColumn;
    @FXML
    private TableColumn<FlightSearchResult, Double> priceColumn;
    @FXML
    private Button acceptButton;

    @FXML
    private void initialize() {
        if(!isBuyingAfterReserve){
            this.preOrder = TicketSaleController.preOrder;
        }else {
            this.preOrder = SearchReserveController.preOrder;
            surnameTextField.setText(SearchReserveController.reservedOrder.getSurname());
            nameTextField.setText(SearchReserveController.reservedOrder.getName());
            documentNumberTextField.setText(SearchReserveController.reservedOrder.getDocument_number());
            organizationTextField.setText(SearchReserveController.reservedOrder.getOrganization_name());
            phoneTextField.setText(SearchReserveController.reservedOrder.getPhone_number());
            emailTextField.setText(SearchReserveController.reservedOrder.getEmail());
            documentTypeComboBox.getSelectionModel().select(SearchReserveController.reservedOrder.getDocument_id());
        }

        orderTable.setItems(this.preOrder);
        initUI();
        initTableView();
    }

    private void initUI() {
        //Combobox - init----
        if (documents == null) try {
            documents = DocumentDAO.getDocuments();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        documentTypeComboBox.setItems(documents);
        documentTypeComboBox.getSelectionModel().selectFirst();
        //---

    }

    private void initTableView() {
        flightNumberColumn.setCellValueFactory(param -> param.getValue().getFlight().flight_numberProperty());
        fromCityColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getFlight().getDeparture_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        toCityColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(CityDAO.getCityById(param.getValue().getFlight().getArrival_city_id()).getCity());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        departureDateTimeColumn.setCellValueFactory(param -> Bindings.concat(
                new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getFlight().getDeparture_date())),
                " ",
                new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getFlight().getDeparture_time()))
        ));
        arrivalDateTimeColumn.setCellValueFactory(param -> Bindings.concat(
                new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getFlight().getArrival_date())),
                " ",
                new ReadOnlyStringWrapper(Util.timeToString(param.getValue().getFlight().getArrival_time()))
        ));
        serviceClassColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(PlaceClassDAO.getPlaceClassById(param.getValue().getPrice_and_place().getClass_id()).getClass_title());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        aviacompanyColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(AirlineDAO.getAirlineById(param.getValue().getFlight().getAirlines_id()).getName());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        priceColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getPrice_and_place().getPrice()));


    }

    @FXML
    private void acceptOrder() {
        passengerArrayList.clear();
        orderArrayList.clear();
        orderDetailsArrayList.clear();
        if (beforeAcceptCheck()) {
            for (FlightSearchResult flightSearchResult : preOrder) {
                Passenger passenger = new Passenger();
                passenger.setName(nameTextField.getText());
                passenger.setSurname(surnameTextField.getText());
                passenger.setPatronymic(patronymicTextField.getText());
                passenger.setDocument_id(documentTypeComboBox.getValue().getDocument_id());
                passenger.setDocument_number(documentNumberTextField.getText());
                passenger.setFlight_id(flightSearchResult.getFlight().getFlight_id());
                passenger.setDate_of_birth(Util.localDateToSqlDate(dateOfBirthDatPicker.getValue()));
                passenger.setIsMan(genderComboBox.getSelectionModel().isSelected(0));
                passenger.setClass_id(flightSearchResult.getPrice_and_place().getClass_id());

                Order order = new Order();
                order.setDocument_id(passenger.getDocument_id());
                order.setDocument_number(passenger.getDocument_number());
                order.setName(passenger.getName());
                order.setSurname(passenger.getSurname());
                order.setEmail(emailTextField.getText().isEmpty() ? null : emailTextField.getText());
                order.setPhone_number(phoneTextField.getText().isEmpty() ? null : phoneTextField.getText());
                order.setOrganization_name(organizationTextField.getText().isEmpty() ? null : organizationTextField.getText());
                order.setPayed(true);

                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setFlight_id(passenger.getFlight_id());
                try {
                    orderDetails.setOrder_id(OrderDAO.getLastOrderId() + 1);
                    passenger.setOrder_id(orderDetails.getOrder_id());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                orderDetails.setClass_id(flightSearchResult.getPrice_and_place().getClass_id());
                orderDetails.setNumber_of_tickets(1);

                try {
                    if(!isBuyingAfterReserve){
                        OrderDAO.acceptOrder(order, orderDetails, passenger, flightSearchResult);
                    }else OrderDAO.acceptOrderAfterReserve(SearchReserveController.reservedOrder.getOrder_id(),passenger, flightSearchResult);

                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    Util.showAlert(Alert.AlertType.ERROR, "Ошибка", null, "Не удалось занести информацию о заказе в базу данных");
                    return;
                }
                MakeOrderController.passengerArrayList.add(passenger);
                MakeOrderController.orderArrayList.add(order);
                MakeOrderController.orderDetailsArrayList.add(orderDetails);
            }
            //Util.showAlert(Alert.AlertType.INFORMATION,"Успешно",null,"Информация о заказе занесена в базу данных");
            Main.controller.currentScreen = Main.PRINT_TICKET;
            Main.controller.myController.loadScreen(Main.PRINT_TICKET,
                    Main.PRINT_TICKET_FXML);
            Main.controller.myController.setScreen(Main.PRINT_TICKET);
        }
    }

    private boolean beforeAcceptCheck() {
        if (surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || patronymicTextField.getText().isEmpty()
                || documentNumberTextField.getText().isEmpty() || dateOfBirthDatPicker.getValue() == null) {
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
