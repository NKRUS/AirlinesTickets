package ru.nk.tickets.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.collections.ObservableList;
import ru.nk.tickets.model.FlightSearchResult;
import ru.nk.tickets.model.Order;
import ru.nk.tickets.model.OrderDetails;
import ru.nk.tickets.model.Passenger;
import ru.nk.tickets.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by NK on 14.11.2016.
 */
public class OrderDAO {

    public static void acceptOrder(Order order, OrderDetails orderDetails, Passenger passenger) throws SQLException, ClassNotFoundException {
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
                        "date_of_birth, document_id, document_number)\n" +
                        "VALUES ("+passenger.getOrder_id()+","+passenger.getFlight_id()+","+passenger.getClass_id()+",'"+passenger.getSurname()
                        +"','"+passenger.getName()+"','"+passenger.getPatronymic()+"',"+passenger.isIsMan()+",'"+passenger.getDate_of_birth()+"',"+passenger.getDocument_id()+
                        ",'"+passenger.getDocument_number()+"');",
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


}
