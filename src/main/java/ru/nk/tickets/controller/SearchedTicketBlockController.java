package ru.nk.tickets.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.nk.tickets.dao.AirlineDAO;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.PlaceClassDAO;
import ru.nk.tickets.model.Flight;
import ru.nk.tickets.model.FlightSearchResult;
import ru.nk.tickets.model.PriceAndPlace;
import ru.nk.tickets.util.Util;

import java.sql.SQLException;


/**
 * Created by NK on 22.11.2016.
 */
public class SearchedTicketBlockController {
    private Flight flight;
    private PriceAndPlace priceAndPlace;
    private TicketSaleController ticketSaleController;

    @FXML
    private Button choose;

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public PriceAndPlace getPriceAndPlace() {
        return priceAndPlace;
    }

    public void setPriceAndPlace(PriceAndPlace priceAndPlace) {
        this.priceAndPlace = priceAndPlace;
    }


    public void populateValues(FlightSearchResult flightSearchResult){
        flight = flightSearchResult.getFlight();
        priceAndPlace = flightSearchResult.getPrice_and_place();
        try {
            putOnTheBlock(flight,priceAndPlace);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void putOnTheBlock(Flight flight, PriceAndPlace priceAndPlace) throws SQLException, ClassNotFoundException {
        priceLabel.setText(String.valueOf((int)priceAndPlace.getPrice()) + "Р");
        departureTimeLabel.setText(Util.timeToString(flight.getDeparture_time()));
        arrivalTimeLabel.setText(Util.timeToString(flight.getArrival_time()));
        departutreCityLabel.setText(String.valueOf(CityDAO.getCityById(flight.getDeparture_city_id())));
        arrivalCityLabel.setText(String.valueOf(CityDAO.getCityById(flight.getArrival_city_id())));
        departureDateLabel.setText(Util.dateToString(flight.getDeparture_date()));
        arrivalDateLabel.setText(Util.dateToString(flight.getArrival_date()));

        flightDurationLabel.setText(Util.diffBeetwenTwoTimes(flight.getDeparture_date(),flight.getDeparture_time(),
                flight.getArrival_date(),flight.getArrival_time()));

        freeSeatsLabel.setText(String.valueOf(priceAndPlace.getFree_places_count()));
        placeClassLabel.setText(String.valueOf(PlaceClassDAO.getPlaceClassById(priceAndPlace.getClass_id())));

        Image logo = Util.getAviaLogoByName(AirlineDAO.getAirlineById(flight.getAirlines_id()).getName());
        companyLogoImage.setImage(logo);

    }

    @FXML
    private Label priceLabel,departureTimeLabel,arrivalTimeLabel,departutreCityLabel,arrivalCityLabel,
            departureDateLabel,arrivalDateLabel, flightDurationLabel, placeClassLabel, freeSeatsLabel;
    @FXML
    private ImageView companyLogoImage;


    @FXML
    private void onChooseClick(){
        choose.setDisable(true);
        FlightSearchResult fsr = new FlightSearchResult();
        fsr.setFlight(flight);
        fsr.setPrice_and_place(priceAndPlace);
        ticketSaleController.addToOrder(fsr);




    }

    public void setParentController(TicketSaleController ticketSaleController){
        this.ticketSaleController = ticketSaleController;
    }

}
