package ru.nk.tickets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.nk.tickets.controller.MainLayoutController;
import ru.nk.tickets.controller.ScreensController;

/**
 * Created by NK on 13.11.2016.
 */
public class Main extends Application {
    public static final String MAIN_SCREEN = "MainLayout";
    public static final String MAIN_SCREEN_FXML = "/ru/nk/tickets/fxml/MainLayout.fxml";
    public static final String TICKET_SALE_SCREEN = "TicketSale";
    public static final String TICKET_SALE_SCREEN_FXML =
            "/ru/nk/tickets/fxml/TicketSale.fxml";
    public static final String SEARCHED_TICKET_BLOCK_FXML = "/ru/nk/tickets/fxml/SearchedTicketBlock.fxml";
    public static final String MAKE_ORDER_FXML = "/ru/nk/tickets/fxml/MakeOrder.fxml";
    public static final String MAKE_ORDER = "MakeOrder";
    public static final String PRINT_TICKET_FXML = "/ru/nk/tickets/fxml/PrintTicket.fxml";
    public static final String PRINT_TICKET = "PrintTicket";
    public static final String PASSENGERS_ARCHIVE = "PassengersArchive";
    public static final String PASSENGERS_ARCHIVE_FXML = "/ru/nk/tickets/fxml/PassengersArchive.fxml";
    public static final String RESERVE_SCREEN = "Reserve";
    public static final String RESERVE_SCREEN_FXML = "/ru/nk/tickets/fxml/Reserve.fxml";
    public static final String RESERVE_INFORMATION_ADDING = "ReserveInformationAdding";
    public static final String RESERVE_INFORMATION_ADDING_FXML = "/ru/nk/tickets/fxml/ReserveInformationAdding.fxml";
    public static final String RESERVE_RESULT_FXML = "/ru/nk/tickets/fxml/ReserveResult.fxml";
    public static final String RESERVE_RESULT = "ReserveResult";
    public static final String SOLD_LIST = "SoldList";
    public static final String SOLD_LIST_FXML = "/ru/nk/tickets/fxml/SoldList.fxml";
    public static final String SEARCH_RESERVE = "SearchReserve";
    public static final String SEARCH_RESERVE_FXML = "/ru/nk/tickets/fxml/SearchReserve.fxml";
    public static MainLayoutController controller;

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
        controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Информационная система - Продажа авиабилетов");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/ru/nk/tickets/image/plane_icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
