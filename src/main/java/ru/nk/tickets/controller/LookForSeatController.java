package ru.nk.tickets.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ru.nk.tickets.Main;

/**
 * Created by NK on 14.11.2016.
 */
public class LookForSeatController{
    ScreensController myController;

    @FXML
    private void initialize() {
        // TODO
    }

    @FXML
    private void goToMain(ActionEvent event){
        myController.setScreen(Main.MAIN_SCREEN);
    }

    //any required method here
}
