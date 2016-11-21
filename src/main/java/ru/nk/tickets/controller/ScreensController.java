package ru.nk.tickets.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by NK on 14.11.2016.
 */
public class ScreensController {
    private HashMap<String, Node> screens = new HashMap<String, Node>();
    private AnchorPane contentScreen;

    public void setContentScreen(AnchorPane contentScreen) {
        this.contentScreen = contentScreen;
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new
                    FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) { //screen loaded
            final DoubleProperty opacity = contentScreen.opacityProperty();

            //Is there is more than one screen
            if (!contentScreen.getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000),

                                t -> {
                                    //remove displayed screen
                                    contentScreen.getChildren().remove(0);
                                    //add new screen
                                    contentScreen.getChildren().add(0, screens.get(name));
                                    Timeline fadeIn = new Timeline(
                                            new KeyFrame(Duration.ZERO,
                                                    new KeyValue(opacity, 0.0)),
                                            new KeyFrame(new Duration(800),
                                                    new KeyValue(opacity, 1.0)));
                                    fadeIn.play();
                                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                //no one else been displayed, then just show
                contentScreen.setOpacity(0.0);
                contentScreen.getChildren().add(screens.get(name));
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500),
                                new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            AnchorPane.setTopAnchor(screens.get(name), 0.0);
            AnchorPane.setRightAnchor(screens.get(name), 0.0);
            AnchorPane.setLeftAnchor(screens.get(name), 0.0);
            AnchorPane.setBottomAnchor(screens.get(name), 0.0);
            return true;
        } else {
            System.out.println("screen hasn't been loaded!\n");
            return false;
        }
    }
    public boolean unloadScreen(String name) {
        if(screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

}
