package ru.nk.tickets.util;

import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.*;
import ru.nk.tickets.dao.CityDAO;
import ru.nk.tickets.dao.PlaceClassDAO;
import ru.nk.tickets.model.FlightSearchResult;
import ru.nk.tickets.model.Order;
import ru.nk.tickets.model.OrderDetails;
import ru.nk.tickets.model.Passenger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NK on 26.11.2016.
 */
public class TicketMaker {
    public static void makeTicket(Passenger passenger, FlightSearchResult flightSearchResult) throws IOException {
        String name = passenger.getSurname() +"."+passenger.getName().substring(0,1)+"."+passenger.getPatronymic().substring(0,1);
        String date = Util.dateToTicketString(flightSearchResult.getFlight().getDeparture_date());
        String time = Util.timeToString(flightSearchResult.getFlight().getDeparture_time());
        String from = "";
        String where = "";
        try {
            from = CityDAO.getCityById(flightSearchResult.getFlight().getDeparture_city_id()).getCity();
            where = CityDAO.getCityById(flightSearchResult.getFlight().getArrival_city_id()).getCity();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String flightNum = flightSearchResult.getFlight().getFlight_number();
        String seatClass = "";
        try {
            seatClass = PlaceClassDAO.getPlaceClassById(flightSearchResult.getPrice_and_place().getClass_id()).getClass_title();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        Docx docx = new Docx("templates/ticket.docx");
        docx.setVariablePattern(new VariablePattern("#{", "}"));

        // preparing variables
        Variables variables = new Variables();
        variables.addTextVariable(new TextVariable("#{left_name}", name));
        variables.addTextVariable(new TextVariable("#{left_date}", date));
        variables.addTextVariable(new TextVariable("#{left_time}", time));
        variables.addTextVariable(new TextVariable("#{left_from}", from));
        variables.addTextVariable(new TextVariable("#{left_where}", where));
        variables.addTextVariable(new TextVariable("#{left_flight}", flightNum));
        TableVariable tableVariable = new TableVariable();

        List<Variable> nameColumnVariables = new ArrayList<Variable>();
        List<Variable> dateColumnVariables = new ArrayList<Variable>();
        List<Variable> timeColumnVariables = new ArrayList<Variable>();
        List<Variable> fromColumnVariables = new ArrayList<Variable>();
        List<Variable> whereColumnVariables = new ArrayList<Variable>();
        List<Variable> flightColumnVariables = new ArrayList<Variable>();
        List<Variable> classColumnVariables = new ArrayList<Variable>();
        List<Variable> emptyColumnVariables = new ArrayList<Variable>();

        nameColumnVariables.add(new TextVariable("#{name}", name));
        emptyColumnVariables.add(new TextVariable("#{empty}",""));
        dateColumnVariables.add(new TextVariable("#{date}",date));
        timeColumnVariables.add(new TextVariable("#{time}",time));
        fromColumnVariables.add(new TextVariable("#{from}",from));
        whereColumnVariables.add(new TextVariable("#{where}",where));
        flightColumnVariables.add(new TextVariable("#{flight}",flightNum));
        classColumnVariables.add(new TextVariable("#{class}",seatClass));


        tableVariable.addVariable(nameColumnVariables);
        tableVariable.addVariable(emptyColumnVariables);
        tableVariable.addVariable(dateColumnVariables);
        tableVariable.addVariable(timeColumnVariables);
        tableVariable.addVariable(fromColumnVariables);
        tableVariable.addVariable(whereColumnVariables);
        tableVariable.addVariable(flightColumnVariables);
        tableVariable.addVariable(classColumnVariables);

        variables.addTableVariable(tableVariable);


// fill template
        docx.fillTemplate(variables);

// save filled .docx file
        File file = new File("Tickets");
        if(!file.exists()){
            file.mkdir();
            System.err.println("Created Tickets Directory");
        }
        String fileName = "Tickets/"+passenger.getDocument_number()+"_"+flightNum+"_"+date+".docx";
        docx.save(fileName);
        Desktop.getDesktop().open(new File(fileName));
    }
    public static void makeReserveTicket(Order order, OrderDetails orderDetails,FlightSearchResult flightSearchResult) throws IOException {
        String order_number = String.valueOf(orderDetails.getOrder_id());
        String from = "";
        String where = "";
        try {
            from = CityDAO.getCityById(flightSearchResult.getFlight().getDeparture_city_id()).getCity();
            where = CityDAO.getCityById(flightSearchResult.getFlight().getArrival_city_id()).getCity();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String whenDate = Util.dateToTicketString(flightSearchResult.getFlight().getDeparture_date());
        String whenTime = Util.timeToString(flightSearchResult.getFlight().getDeparture_time());
        String seatClass = "";
        try {
            seatClass = PlaceClassDAO.getPlaceClassById(flightSearchResult.getPrice_and_place().getClass_id()).getClass_title();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String seatCount = String.valueOf(orderDetails.getNumber_of_tickets());

        Docx docx = new Docx("templates/reserve.docx");
        docx.setVariablePattern(new VariablePattern("#{", "}"));

        Variables variables = new Variables();
        TableVariable tableVariable = new TableVariable();
        List<Variable> orderNumberColumnVariables = new ArrayList<Variable>();
        List<Variable> fromColumnVariables = new ArrayList<Variable>();
        List<Variable> whereColumnVariables = new ArrayList<Variable>();
        List<Variable> whenColumnVariables = new ArrayList<Variable>();
        List<Variable> seatClassColumnVariables = new ArrayList<Variable>();
        List<Variable> seatCountColumnVariables = new ArrayList<Variable>();
        List<Variable> emptyCountColumnVariables = new ArrayList<Variable>();

        orderNumberColumnVariables.add(new TextVariable("#{order_number}", order_number));
        fromColumnVariables.add(new TextVariable("#{from}", from));
        whereColumnVariables.add(new TextVariable("#{where}", where));
        whenColumnVariables.add(new TextVariable("#{when}", whenDate+"/"+whenTime));
        seatClassColumnVariables.add(new TextVariable("#{class}", seatClass));
        seatCountColumnVariables.add(new TextVariable("#{count}", seatCount));
        emptyCountColumnVariables.add(new TextVariable("#{e}", ""));

        tableVariable.addVariable(orderNumberColumnVariables);
        tableVariable.addVariable(fromColumnVariables);
        tableVariable.addVariable(whereColumnVariables);
        tableVariable.addVariable(whenColumnVariables);
        tableVariable.addVariable(seatClassColumnVariables);
        tableVariable.addVariable(seatCountColumnVariables);
        tableVariable.addVariable(emptyCountColumnVariables);

        variables.addTableVariable(tableVariable);


// fill template
        docx.fillTemplate(variables);
        docx.fillTemplate(variables);

// save filled .docx file
        File file = new File("Reserves");
        if(!file.exists()){
            file.mkdir();
            System.err.println("Created Reserves Directory");
        }
        String fileName = "Reserves/"+orderDetails.getFlight_id()+"_"+order_number+"_reserve"+".docx";
        docx.save(fileName);
        Desktop.getDesktop().open(new File(fileName));

    }

    public static void main(String[] args) throws IOException {

    }

}
