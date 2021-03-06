package ru.nk.tickets.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.*;
import ru.nk.tickets.util.DBUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Created by NK on 14.11.2016.
 */
public class OrderDAO {

    public static void acceptOrder(Order order, OrderDetails orderDetails, Passenger passenger, FlightSearchResult flightSearchResult) throws SQLException, ClassNotFoundException {
        String[] queries = {
                "INSERT INTO orders(surname,name,organization_name,phone_number,email,payed,document_id,document_number)\n" +
                        "VALUES('"+order.getSurname()+"','"+order.getName()+"',"+(order.getOrganization_name()==null?null:"'"+order.getOrganization_name()+"'")+","
                        +(order.getPhone_number()==null?null:"'"+order.getPhone_number()+"'")+","
                        +(order.getEmail()==null?null:"'"+order.getEmail()+"'")+","+order.isPayed()+","+order.getDocument_id()+",'"+order.getDocument_number()+"');",
                "INSERT INTO order_details(order_id,flight_id,class_id,number_of_tickets) " +
                        "VALUES("+orderDetails.getOrder_id()+","+orderDetails.getFlight_id()+","+orderDetails.getClass_id()+","+orderDetails.getNumber_of_tickets()+");",
                "INSERT INTO passengers (order_id, flight_id, class_id, surname, name, patronymic, gender, \n" +
                        "date_of_birth, document_id, document_number)\n" +
                        "VALUES ("+passenger.getOrder_id()+","+passenger.getFlight_id()+","+passenger.getClass_id()+",'"+passenger.getSurname()
                        +"','"+passenger.getName()+"','"+passenger.getPatronymic()+"',"+passenger.isIsMan()+",'"+passenger.getDate_of_birth()+"',"+passenger.getDocument_id()+
                        ",'"+passenger.getDocument_number()+"');",
                "INSERT INTO passengers_archive (order_id, flight_id, class_id, surname, name, patronymic, gender, \n" +
                        "date_of_birth, document_id, document_number, flight_number, airline, departure_city, arrival_city, departure_date)\n" +
                        "VALUES ("+passenger.getOrder_id()+","+passenger.getFlight_id()+","+passenger.getClass_id()+",'"+passenger.getSurname()
                        +"','"+passenger.getName()+"','"+passenger.getPatronymic()+"',"+passenger.isIsMan()+",'"+passenger.getDate_of_birth()+"',"+passenger.getDocument_id()+
                        ",'"+passenger.getDocument_number()+"','"+flightSearchResult.getFlight().getFlight_number()+"','"+AirlineDAO.getAirlineById(flightSearchResult.getFlight().getAirlines_id())
                        +"','"+CityDAO.getCityById(flightSearchResult.getFlight().getDeparture_city_id())+"','"+CityDAO.getCityById(flightSearchResult.getFlight().getArrival_city_id())
                        +"','"+flightSearchResult.getFlight().getDeparture_date()+"');",
                "UPDATE prices_and_places \n" +
                        "SET free_places_count = free_places_count - 1\n" +
                        "WHERE flight_id="+passenger.getFlight_id()+" AND class_id="+orderDetails.getClass_id()+";"

        };
        System.out.println(queries[0]);
        System.out.println(queries[1]);
        System.out.println(queries[2]);
        System.out.println(queries[3]);

        try {
            for (String query :queries) {
                DBUtil.dbExecuteUpdate(query);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }
    public static void acceptOrderAfterReserve(int order_id,Passenger passenger, FlightSearchResult flightSearchResult) throws SQLException, ClassNotFoundException {
        String[] queries = {
                "UPDATE orders SET payed=true WHERE id="+order_id+";",
                "INSERT INTO passengers (order_id, flight_id, class_id, surname, name, patronymic, gender, \n" +
                        "date_of_birth, document_id, document_number)\n" +
                        "VALUES ("+order_id+","+passenger.getFlight_id()+","+passenger.getClass_id()+",'"+passenger.getSurname()
                        +"','"+passenger.getName()+"','"+passenger.getPatronymic()+"',"+passenger.isIsMan()+",'"+passenger.getDate_of_birth()+"',"+passenger.getDocument_id()+
                        ",'"+passenger.getDocument_number()+"');",
                "INSERT INTO passengers_archive (order_id, flight_id, class_id, surname, name, patronymic, gender, \n" +
                        "date_of_birth, document_id, document_number, flight_number, airline, departure_city, arrival_city, departure_date)\n" +
                        "VALUES ("+order_id+","+passenger.getFlight_id()+","+passenger.getClass_id()+",'"+passenger.getSurname()
                        +"','"+passenger.getName()+"','"+passenger.getPatronymic()+"',"+passenger.isIsMan()+",'"+passenger.getDate_of_birth()+"',"+passenger.getDocument_id()+
                        ",'"+passenger.getDocument_number()+"','"+flightSearchResult.getFlight().getFlight_number()+"','"+AirlineDAO.getAirlineById(flightSearchResult.getFlight().getAirlines_id())
                        +"','"+CityDAO.getCityById(flightSearchResult.getFlight().getDeparture_city_id())+"','"+CityDAO.getCityById(flightSearchResult.getFlight().getArrival_city_id())
                        +"','"+flightSearchResult.getFlight().getDeparture_date()+"');"


        };
        System.out.println(queries[0]);
        System.out.println(queries[1]);
        System.out.println(queries[2]);

        try {
            for (String query :queries) {
                DBUtil.dbExecuteUpdate(query);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void acceptReserve(Order order, OrderDetails orderDetails) throws SQLException, ClassNotFoundException{
        String[] queries = {
                "INSERT INTO orders(surname,name,organization_name,phone_number,email,payed,document_id,document_number)\n" +
                        "VALUES('"+order.getSurname()+"','"+order.getName()+"',"+(order.getOrganization_name()==null?null:"'"+order.getOrganization_name()+"'")+","
                        +(order.getPhone_number()==null?null:"'"+order.getPhone_number()+"'")+","
                        +(order.getEmail()==null?null:"'"+order.getEmail()+"'")+","+order.isPayed()+","+order.getDocument_id()+",'"+order.getDocument_number()+"');",
                "INSERT INTO order_details(order_id,flight_id,class_id,number_of_tickets) " +
                        "VALUES("+orderDetails.getOrder_id()+","+orderDetails.getFlight_id()+","+orderDetails.getClass_id()+","+orderDetails.getNumber_of_tickets()+");",
                "UPDATE prices_and_places \n" +
                        "SET free_places_count = free_places_count - "+orderDetails.getNumber_of_tickets()+"\n" +
                        "WHERE flight_id="+orderDetails.getFlight_id()+" AND class_id="+orderDetails.getClass_id()+";"

        };
        System.out.println(queries[0]);
        System.out.println(queries[1]);
        System.out.println(queries[2]);

        try {
            for (String query :queries) {
                DBUtil.dbExecuteUpdate(query);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static int getLastOrderId() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT MAX(id) AS id FROM orders;";
        try{
            ResultSet rsOrderId = DBUtil.dbExecuteQuery(selectStmt);
            int id = 0;
            if(rsOrderId.next()) id = rsOrderId.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ObservableList<SoldTickets> getSoldTickets() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT flights.*, order_details.flight_id,order_details.class_id,sum(order_details.number_of_tickets) AS sold_tickets, pap.free_places_count FROM order_details\n" +
                "INNER JOIN orders o\n" +
                "ON o.id = order_details.order_id AND o.payed=true\n" +
                "INNER JOIN flights\n" +
                "ON order_details.flight_id = flights.id\n" +
                "INNER JOIN prices_and_places pap\n" +
                "ON order_details.flight_id = pap.flight_id AND order_details.class_id = pap.class_id\n" +
                "GROUP BY flight_id, class_id\n" +
                "ORDER BY flights.flight_number;";

        try {
            ResultSet rsST = DBUtil.dbExecuteQuery(selectStmt);
            ObservableList<SoldTickets> soldTicketsList = getSoldTicketsList(rsST);
            return soldTicketsList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ObservableList<SoldTickets> getSoldTicketsList(ResultSet resultSet) throws SQLException {
        ObservableList<SoldTickets> soldTicketsList = FXCollections.observableArrayList();

        while (resultSet.next()){
            SoldTickets soldTickets = new SoldTickets();
            Flight flight = new Flight();
            flight.setFlight_id(resultSet.getInt("id"));
            flight.setFlight_number(resultSet.getString("flight_number"));
            flight.setAirlines_id(resultSet.getInt("airlines_id"));
            flight.setDeparture_city_id(resultSet.getInt("departure_city_id"));
            flight.setArrival_city_id(resultSet.getInt("arrival_city_id"));
            flight.setDeparture_date((Date) resultSet.getObject("departure_date"));
            flight.setDeparture_time((Time) resultSet.getObject("departure_time"));
            flight.setArrival_date((Date) resultSet.getObject("arrival_date"));
            flight.setArrival_time((Time) resultSet.getObject("arrival_time"));
            soldTickets.setFlight(flight);
            soldTickets.setClass_id(resultSet.getInt("class_id"));
            soldTickets.setSold_tickets(resultSet.getInt("sold_tickets"));
            soldTickets.setFree_places_count(resultSet.getInt("free_places_count"));
            soldTicketsList.add(soldTickets);
        }
        return soldTicketsList;
    }

    public static ObservableList<ReservedTickets> searchReserve(String surname, String organization) throws SQLException, ClassNotFoundException {
        String select = "";
        select = "SELECT orders.*, flights.flight_number, flights.departure_city_id, flights.arrival_city_id, flights.departure_date, airlines.name AS airline \n" +
                "FROM avia.orders\n" +
                "INNER JOIN order_details\n" +
                "ON orders.id=order_details.order_id\n" +
                "INNER JOIN flights\n" +
                "ON order_details.flight_id=flights.id\n" +
                "INNER JOIN airlines\n" +
                "ON flights.airlines_id=airlines.id\n" +
                "WHERE payed=false "+(surname!=null? " AND surname = '"+surname+"'":"")+ (organization!=null?" AND organization_name='"+organization+"'" :"")+
                " ORDER BY flights.flight_number;";


        /*if(surname!=null && organization!=null){
            select = "SELECT * FROM orders\n" +
                    "WHERE surname = '"+surname+"' AND organization_name = '"+organization+"' AND payed=false;";
        }else if(surname != null){
            select = "SELECT * FROM orders\n" +
                    "WHERE surname = '"+surname+"' AND payed=false;";
        }else if(organization!=null){
            select = "SELECT * FROM orders\n" +
                    "WHERE organization_name = '"+organization+"' AND payed=false;";
        }
*/
        try {
            ResultSet rsR = DBUtil.dbExecuteQuery(select);
            return getOrderSearchResultList(rsR);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

    }

    private static ObservableList<ReservedTickets> getOrderSearchResultList(ResultSet rsR) throws SQLException, ClassNotFoundException {
        ObservableList<ReservedTickets> oOL = FXCollections.observableArrayList();

        while (rsR.next()){
            ReservedTickets reservedTickets = new ReservedTickets();
            Order order = new Order();
            order.setOrder_id(rsR.getInt("id"));
            order.setSurname(rsR.getString("surname"));
            order.setName(rsR.getString("name"));
            order.setOrganization_name(rsR.getString("organization_name"));
            order.setPhone_number(rsR.getString("phone_number"));
            order.setEmail(rsR.getString("email"));
            order.setPayed(rsR.getBoolean("payed"));
            order.setDocument_id(rsR.getInt("document_id"));
            order.setDocument_number(rsR.getString("document_number"));
            reservedTickets.setOrder(order);

            for (int i = 1; i <= rsR.getMetaData().getColumnCount(); i++) {
                System.out.println(rsR.getMetaData().getColumnName(i));
            }
            reservedTickets.setFlight_number(rsR.getString("flight_number"));
            reservedTickets.setDeparture_city(CityDAO.getCityById(rsR.getInt("departure_city_id")).getCity());
            reservedTickets.setArrival_city(CityDAO.getCityById(rsR.getInt("arrival_city_id")).getCity());
            reservedTickets.setDeparture_date((Date) rsR.getObject("departure_date"));
            reservedTickets.setAirline(rsR.getString(rsR.getMetaData().getColumnCount()));

            oOL.add(reservedTickets);
        }
        return oOL;
    }

    private static OrderDetails getOrderDetailsFromResultSet(ResultSet rs) throws SQLException {
        OrderDetails orderDetails = null;
        if (rs.next()){
            orderDetails = new OrderDetails();
            orderDetails.setId(rs.getInt("id"));
            orderDetails.setOrder_id(rs.getInt("order_id"));
            orderDetails.setFlight_id(rs.getInt("flight_id"));
            orderDetails.setClass_id(rs.getInt("class_id"));
            orderDetails.setNumber_of_tickets(rs.getInt("number_of_tickets"));
        }
        return orderDetails;
    }

    public static OrderDetails getOrderDetailsByOrderId(int order_id) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM order_details WHERE order_id="+order_id;

        try{
            ResultSet rsOrderDetails = DBUtil.dbExecuteQuery(selectStmt);
            OrderDetails orderDetails = getOrderDetailsFromResultSet(rsOrderDetails);
            return orderDetails;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
