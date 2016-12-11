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
import ru.nk.tickets.model.ArchivePassenger;
import ru.nk.tickets.model.Passenger;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;

/**
 * Created by NK on 27.11.2016.
 */
public class PassengersArchiveController {

    public static ObservableList<ArchivePassenger> passengers = FXCollections.observableArrayList();

    @FXML
    private TableView<ArchivePassenger> archiveTable;
    @FXML
    private TableColumn<ArchivePassenger, String> surnameColumn, nameColumn, patronymicColumn,genderColumn,
            dateOfBirthColumn,documentTypeColumn,documentNumberColumn, flightTableColumn, departureDateTableColumn;


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
        surnameColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPassenger().getSurname()) );
        nameColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPassenger().getName()) );
        patronymicColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPassenger().getPatronymic()));
        genderColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPassenger().isIsMan()?"Мужчина":"Женщина"));
        dateOfBirthColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getPassenger().getDate_of_birth())));
        documentTypeColumn.setCellValueFactory(param -> {
            try {
                return new ReadOnlyStringWrapper(DocumentDAO.getDocuments().get(param.getValue().getPassenger().getDocument_id()-1).getDocument_type());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        });
        documentNumberColumn.setCellValueFactory(param -> param.getValue().getPassenger().document_numberProperty());
        flightTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getAirline()+ " " +param.getValue().getFlight_number() + " " + param.getValue().getDeparture_city()
        + " - " + param.getValue().getArrival_city()));
        departureDateTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(Util.dateToTicketString(param.getValue().getDeparture_date())));

    }
}
