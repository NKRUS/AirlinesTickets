package ru.nk.tickets.model;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by NK on 21.11.2016.
 */
public class FlightSearchResult {
    private SimpleObjectProperty<Flight> flight;
    private SimpleObjectProperty<PriceAndPlace> price_and_place;

    public FlightSearchResult() {
        flight = new SimpleObjectProperty<Flight>();
        price_and_place = new SimpleObjectProperty<PriceAndPlace>();
    }

    public Flight getFlight() {
        return flight.get();
    }

    public SimpleObjectProperty<Flight> flightProperty() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight.set(flight);
    }

    public PriceAndPlace getPrice_and_place() {
        return price_and_place.get();
    }

    public SimpleObjectProperty<PriceAndPlace> price_and_placeProperty() {
        return price_and_place;
    }

    public void setPrice_and_place(PriceAndPlace price_and_place) {
        this.price_and_place.set(price_and_place);
    }

    @Override
    public String toString() {
        return "FlightSearchResult{" +
                "flight=" + flight +
                ", price_and_place=" + price_and_place +
                '}';
    }
}



