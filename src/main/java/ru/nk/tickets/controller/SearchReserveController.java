package ru.nk.tickets.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.nk.tickets.dao.DocumentDAO;
import ru.nk.tickets.dao.OrderDAO;
import ru.nk.tickets.model.Order;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class SearchReserveController {
    public static ObservableList<Order> reservedList = FXCollections.observableArrayList();
    @FXML
    private TextField surnameTextField, organizationTextField;
    @FXML
    private TableView<Order> reservedTableView;
    @FXML
    private TableColumn<Order,String> idTableColumn, surnameTableColumn, nameTableColumn, organizationTableColumn, phoneNumberTableColumn,
    emailTableColumn, documentTypeTableColumn, documentNumberTableColumn;



    @FXML
    private void initialize() {


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

        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getOrder_id())));
        surnameTableColumn.setCellValueFactory(param -> param.getValue().surnameProperty());
        nameTableColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        organizationTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getOrganization_name()==null?"---":param.getValue().getOrganization_name()));
        phoneNumberTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getPhone_number()==null?"---":param.getValue().getPhone_number()
        ));
        emailTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().getEmail()==null?"---":param.getValue().getEmail()
        ));
        documentTypeTableColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(DocumentDAO.getDocuments().get(param.getValue().getDocument_id()-1).getDocument_type());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        documentNumberTableColumn.setCellValueFactory(param -> param.getValue().document_numberProperty());
    }
}
