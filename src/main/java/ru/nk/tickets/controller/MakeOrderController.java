package ru.nk.tickets.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.nk.tickets.dao.*;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.DBUtil;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;


/**
 * Created by NK on 22.11.2016.
 */
public class MakeOrderController {

    private ObservableList<Document> documents;

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
    private TableView orderTable;
    @FXML
    private TableColumn<FlightSearchResult,String> flightNumberColumn, fromCityColumn, toCityColumn, departureDateTimeColumn,
    arrivalDateTimeColumn, serviceClassColumn,aviacompanyColumn;
    @FXML
    private TableColumn<FlightSearchResult,Double> priceColumn;


    @FXML
    private void initialize(){
        orderTable.setItems(TicketSaleController.preOrder);
        initUI();
        initTableView();
    }

    private void initUI(){
        //Combobox - init----
        if(documents==null) try {
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

    private void initTableView(){
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
                param.getValue().getFlight().departure_dateProperty(),
                " ",
                param.getValue().getFlight().departure_timeProperty()
        ));
        arrivalDateTimeColumn.setCellValueFactory(param -> Bindings.concat(
                param.getValue().getFlight().arrival_dateProperty(),
                " ",
                param.getValue().getFlight().arrival_timeProperty()
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
    private void acceptOrder(){
        if(beforeAcceptCheck()){
            for (FlightSearchResult flightSearchResult : TicketSaleController.preOrder) {
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
                order.setEmail(emailTextField.getText().isEmpty()?null:emailTextField.getText());
                order.setPhone_number(phoneTextField.getText().isEmpty()?null:phoneTextField.getText());
                order.setOrganization_name(organizationTextField.getText().isEmpty()?null:organizationTextField.getText());
                order.setPayed(true);

                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setFlight_id(passenger.getFlight_id());
                try {
                    orderDetails.setOrder_id(OrderDAO.getLastOrderId()+1);
                    passenger.setOrder_id(orderDetails.getOrder_id());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                orderDetails.setClass_id(flightSearchResult.getPrice_and_place().getClass_id());
                orderDetails.setNumber_of_tickets(1);

                OrderDAO.acceptOrder(order,orderDetails,passenger);

            }
            Util.showAlert(Alert.AlertType.INFORMATION,"Успешно",null,"Информация о заказе занесена в базу данных");

        }
    }

    private boolean beforeAcceptCheck(){
        if(surnameTextField.getText().isEmpty() || nameTextField.getText().isEmpty() || patronymicTextField.getText().isEmpty()
                || documentNumberTextField.getText().isEmpty() || dateOfBirthDatPicker.getValue()==null){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание",null,"Пожалуйста заполните обязательные поля!");
            return false;
        }
        if(!Util.validateOnlyNumbers(documentNumberTextField.getText())){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание",null,"Поле Номер документа может содержать только цифры и пробелы!");
            documentNumberTextField.requestFocus();
            return false;
        }

        if(!phoneTextField.getText().isEmpty() && !Util.validateOnlyNumbers(phoneTextField.getText())){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание",null,"Поле Номер телефона* может содержать только цифры и пробелы!");
            phoneTextField.requestFocus();
            return false;
        }

        return true;
    }




}
