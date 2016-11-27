package ru.nk.tickets.controller;

import ru.nk.tickets.model.FlightSearchResult;

/**
 * Created by NK on 27.11.2016.
 */
public interface SearchableTicketScreen {
    public void addToOrder(FlightSearchResult flightSearchResult);
}
