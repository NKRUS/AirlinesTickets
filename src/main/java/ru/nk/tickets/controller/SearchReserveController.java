package ru.nk.tickets.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import ru.nk.tickets.Main;
import ru.nk.tickets.dao.*;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class SearchReserveController {
    private static ObservableList<ReservedTickets> reservedList = FXCollections.observableArrayList();
    public static ObservableList<FlightSearchResult> preOrder = FXCollections.observableArrayList();
    public static Order reservedOrder;
    @FXML
    private TextField surnameTextField, organizationTextField;
    @FXML
    private TableView<ReservedTickets> reservedTableView;
    @FXML
    private TableColumn<ReservedTickets,String> idTableColumn, surnameTableColumn, nameTableColumn, organizationTableColumn, phoneNumberTableColumn,
    emailTableColumn, documentTypeTableColumn, documentNumberTableColumn, flightTableColumn, departureDateTableColumn;



    @FXML
    private void initialize() {

        reservedTableView.setRowFactory( tv -> {
            TableRow<ReservedTickets> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    PopOver popOver = new PopOver();
                    popOver = new PopOver();
                    Button button = new Button();
                    button.setText("Выкупить");
                    PopOver finalPopOver = popOver;
                    button.setOnAction(event1 -> {
                        buyTicket(row.getItem().getOrder());
                        finalPopOver.hide();
                    });//Событие выкупа билета и перехода на следующую форму
                    popOver.setContentNode(button);
                    popOver.setDetachable(false);
                    popOver.setDetached(false);
                    popOver.fadeInDurationProperty().setValue(new Duration(1000));
                    popOver.setCornerRadius(4);
                    popOver.show(row);
                }
            });
            return row ;
        });
    }

    private void buyTicket(Order order){
        preOrder.clear();
        try {
            OrderDetails orderDetails = OrderDAO.getOrderDetailsByOrderId(order.getOrder_id());
            Flight flight = FlightDAO.getFlightById(orderDetails.getFlight_id());
            PriceAndPlace priceAndPlace = PriceAndPlaceDAO.getPriceAndPlaceById(orderDetails.getFlight_id(),orderDetails.getClass_id());

            FlightSearchResult flightSearchResult = new FlightSearchResult();
            flightSearchResult.setFlight(flight);
            flightSearchResult.setPrice_and_place(priceAndPlace);
            reservedOrder = order;
            preOrder.add(flightSearchResult);

        } catch (SQLException | ClassNotFoundException e) {
            Util.showAlert(Alert.AlertType.ERROR,"Ошибка",null,"Не удалось приобрести билет");
            e.printStackTrace();
            return;
        }
        MakeOrderController.isBuyingAfterReserve = true;
        System.err.println("Succesfully loading screen for Make Order");
        Main.controller.currentScreen = Main.MAKE_ORDER;
        Main.controller.myController.loadScreen(Main.MAKE_ORDER,
                Main.MAKE_ORDER_FXML);
        Main.controller.myController.setScreen(Main.MAKE_ORDER);

    }

    @FXML
    private void lookForReserve(){
        if (beforeCheck()) {
            reservedList.clear();
            String surname = surnameTextField.getText().isEmpty()?null:surnameTextField.getText();
            String organization = organizationTextField.getText().isEmpty()?null:organizationTextField.getText();
            try {
                reservedList = OrderDAO.searchReserve(surname,organization);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            initTable();
        }
    }
    private boolean beforeCheck(){
        if(surnameTextField.getText().isEmpty() && organizationTextField.getText().isEmpty()){
            Util.showAlert(Alert.AlertType.WARNING,"Внимание",null,"Заполните одно из полей для поиска!");
            return false;
        }
        return true;
    }

    private void initTable() {
        reservedTableView.getItems().clear();
        if (reservedList.size()==0) Util.showAlert(Alert.AlertType.INFORMATION,"Внимание",null,"По вашему запросу ничего не найдено!");
        reservedTableView.setItems(reservedList);

        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getOrder().getOrder_id())));
        surnameTableColumn.setCellValueFactory(param -> param.getValue().getOrder().surnameProperty());
        nameTableColumn.setCellValueFactory(param -> param.getValue().getOrder().nameProperty());
        organizationTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getOrder().getOrganization_name()==null?"---":param.getValue().getOrder().getOrganization_name()));
        phoneNumberTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getOrder().getPhone_number()==null?"---":param.getValue().getOrder().getPhone_number()
        ));
        emailTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getOrder().getEmail()==null?"---":param.getValue().getOrder().getEmail()
        ));
        documentTypeTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(DocumentDAO.getDocuments().get(param.getValue().getOrder().getDocument_id()-1).getDocument_type());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        documentNumberTableColumn.setCellValueFactory(param -> param.getValue().getOrder().document_numberProperty());
        flightTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getAirline()+ " " +param.getValue().getFlight_number() + " " + param.getValue().getDeparture_city()
                + " - " + param.getValue().getArrival_city()));
        departureDateTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getDeparture_date())));


    }
}
