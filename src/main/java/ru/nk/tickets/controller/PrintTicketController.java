package ru.nk.tickets.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import ru.nk.tickets.Main;
import ru.nk.tickets.util.TicketMaker;
import ru.nk.tickets.util.Util;

import java.io.IOException;

/**
 * Created by NK on 27.11.2016.
 */
public class PrintTicketController {
    @FXML
    private Button continueWork, printTickets;

    @FXML
    private void initialize() {
        continueWork.setOnAction(event -> {
            Main.controller.currentScreen = Main.TICKET_SALE_SCREEN;
            Main.controller.myController.loadScreen(Main.TICKET_SALE_SCREEN,
                    Main.TICKET_SALE_SCREEN_FXML);
            Main.controller.myController.setScreen(Main.TICKET_SALE_SCREEN);
        });

        printTickets.setOnAction(event -> {
            for (int i = 0; i < TicketSaleController.preOrder.size(); i++) {
                try {
                    TicketMaker.makeTicket(MakeOrderController.passengerArrayList.get(i), TicketSaleController.preOrder.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                    Util.showAlert(Alert.AlertType.WARNING,"Ошибка",null,"Не удалось распечатать билеты");
                }
            }

        });
    }
}
