package ru.nk.tickets.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ru.nk.tickets.Main;
import ru.nk.tickets.model.FlightSearchResult;

import java.io.IOException;

/**
 * Created by NK on 14.11.2016.
 */
public class MainLayoutController  {
    String currentScreen = "";

    @FXML
    private AnchorPane mainContent;

    ScreensController myController;

    @FXML
    private void initialize() {
        myController =  new ScreensController();
        myController.setContentScreen(mainContent);
    }

    public AnchorPane getMainContent() {
        return this.mainContent;
    }


    @FXML
    private void sellTicket() throws IOException {
       if(!currentScreen.equals(Main.TICKET_SALE_SCREEN)) {
           currentScreen = Main.TICKET_SALE_SCREEN;
           myController.loadScreen(Main.TICKET_SALE_SCREEN,
                   Main.TICKET_SALE_SCREEN_FXML);
           myController.setScreen(Main.TICKET_SALE_SCREEN);
       }
    }

    @FXML
    private void lookForSeat(){
        if(!currentScreen.equals(Main.LOOK_FOR_SEAT_SCREEN)) {
            currentScreen = Main.LOOK_FOR_SEAT_SCREEN;
            myController.loadScreen(Main.LOOK_FOR_SEAT_SCREEN,
                    Main.LOOK_FOR_SEAT_SCREEN_FXML);
            myController.setScreen(Main.LOOK_FOR_SEAT_SCREEN);
        }
    }

    @FXML
    private void passengersArchive(){
        if(!currentScreen.equals(Main.PASSENGERS_ARCHIVE)) {
            currentScreen = Main.PASSENGERS_ARCHIVE;
            myController.loadScreen(Main.PASSENGERS_ARCHIVE,
                    Main.PASSENGERS_ARCHIVE_FXML);
            myController.setScreen(Main.PASSENGERS_ARCHIVE);
        }
    }

    @FXML
    private void reserveTicket(){
        if(!currentScreen.equals(Main.RESERVE_SCREEN)) {
            currentScreen = Main.RESERVE_SCREEN;
            myController.loadScreen(Main.RESERVE_SCREEN,
                    Main.RESERVE_SCREEN_FXML);
            myController.setScreen(Main.RESERVE_SCREEN);
        }
    }


    //any required method here
}
