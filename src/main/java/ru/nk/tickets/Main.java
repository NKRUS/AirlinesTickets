package ru.nk.tickets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nk.tickets.controller.ScreensController;

/**
 * Created by NK on 13.11.2016.
 */
public class Main extends Application {
    public static final String MAIN_SCREEN = "MainLayout";
    public static final String MAIN_SCREEN_FXML = "/ru/nk/tickets/fxml/MainLayout.fxml";
    public static final String LOOK_FOR_SEAT_SCREEN = "LookForSeat";
    public static final String LOOK_FOR_SEAT_SCREEN_FXML =
            "/ru/nk/tickets/fxml/LookForSeat.fxml";
    public static final String TICKET_SALE_SCREEN = "TicketSale";
    public static final String TICKET_SALE_SCREEN_FXML =
            "/ru/nk/tickets/fxml/TicketSale.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        /*ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.MAIN_SCREEN,
                Main.MAIN_SCREEN_FXML);
        mainContainer.loadScreen(Main.LOOK_FOR_SEAT_SCREEN,
                Main.LOOK_FOR_SEAT_SCREEN_FXML);
        mainContainer.loadScreen(Main.TICKET_SALE_SCREEN,
                Main.TICKET_SALE_SCREEN_FXML);

        mainContainer.setScreen(Main.MAIN_SCREEN);*/

        //Group root = new Group();
        //root.getChildren().addAll(mainContainer);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(MAIN_SCREEN_FXML));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Информационная система - Продажа авиабилетов");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ru/nk/tickets/image/mai_logo.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
