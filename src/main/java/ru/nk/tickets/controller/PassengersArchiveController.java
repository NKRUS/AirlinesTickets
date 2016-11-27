package ru.nk.tickets.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.nk.tickets.dao.DocumentDAO;
import ru.nk.tickets.dao.PassengerDAO;
import ru.nk.tickets.model.Passenger;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class PassengersArchiveController {

    public static ObservableList<Passenger> passengers = FXCollections.observableArrayList();

    @FXML
    private TableView<Passenger> archiveTable;
    @FXML
    private TableColumn<Passenger, String> idColumn, surnameColumn, nameColumn, patronymicColumn,genderColumn,
            dateOfBirthColumn,documentTypeColumn,documentNumberColumn;


    public PassengersArchiveController(){


    }

    @FXML
    private void initialize() {
        try {
            passengers = PassengerDAO.getPassengersFromArchive();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Util.showAlert(Alert.AlertType.WARNING,"Ошибка",null,"Возникла проблема при считывании архива пассажиров");
        }
        initTable();
    }

    private void initTable(){
        archiveTable.setItems(passengers);
        idColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getPassenger_id())));
        surnameColumn.setCellValueFactory(param -> param.getValue().surnameProperty());
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        patronymicColumn.setCellValueFactory(param -> param.getValue().patronymicProperty());
        genderColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().isIsMan()?"Мужчина":"Женщина"));
        dateOfBirthColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getDate_of_birth())));
        documentTypeColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(DocumentDAO.getDocuments().get(param.getValue().getDocument_id()-1).getDocument_type());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        documentNumberColumn.setCellValueFactory(param -> param.getValue().document_numberProperty());
    }
}
