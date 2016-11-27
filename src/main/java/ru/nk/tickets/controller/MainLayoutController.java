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
    @FXML
    private void soldList(){
        if(!currentScreen.equals(Main.SOLD_LIST)) {
            currentScreen = Main.SOLD_LIST;
            myController.loadScreen(Main.SOLD_LIST,
                    Main.SOLD_LIST_FXML);
            myController.setScreen(Main.SOLD_LIST);
        }
    }

    @FXML
    private void searchReserve(){
        if(!currentScreen.equals(Main.SEARCH_RESERVE)) {
            currentScreen = Main.SEARCH_RESERVE;
            myController.loadScreen(Main.SEARCH_RESERVE,
                    Main.SEARCH_RESERVE_FXML);
            myController.setScreen(Main.SEARCH_RESERVE);
        }
    }

    @FXML
    private void flightsAdding(){
        if(!currentScreen.equals(Main.FLIGHT_OPERATE)) {
            currentScreen = Main.FLIGHT_OPERATE;
            myController.loadScreen(Main.FLIGHT_OPERATE,
                    Main.FLIGHT_OPERATE_FXML);
            myController.setScreen(Main.FLIGHT_OPERATE);
        }
    }


    //any required method here
}
