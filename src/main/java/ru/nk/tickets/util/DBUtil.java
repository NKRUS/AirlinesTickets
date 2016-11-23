package ru.nk.tickets.util;

import com.sun.rowset.CachedRowSetImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by NK on 13.11.2016.
 */
public class DBUtil {
    static final Properties prop = new Properties();
    static {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().
                    getResourceAsStream("ru/nk/tickets/config.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            System.err.println("File config.properties not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Cannot read from file config.properties");
            e.printStackTrace();
        }
    }

    //Объявляем JDBC драйвер
    private static final String JDBC_DRIVER = prop.getProperty("jdbc.driver");;
    private static final String CONN_STR = prop.getProperty("jdbc.url");
    private static final String DB_USERNAME = prop.getProperty("jdbc.username");
    private static final String DB_PASSWORD = prop.getProperty("jdbc.password");
    //Соединение с базой данных
    private static Connection conn = null;

    public static void dbConnect() throws ClassNotFoundException, SQLException {
        //Загружаем драйвер
        /*try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            throw e;
        }*/

        System.err.println("MySQL Driver is registered!");
        //Подключаемся к базе данных
        try {
            conn = DriverManager.getConnection(CONN_STR,DB_USERNAME,DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }

    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e){
            throw e;
        }
    }

    public static void dbExecuteUpdateBatch(String[] sqlStmts) throws SQLException {
        Statement stmt = null;
        try {
            //Подключаемся к базе
            dbConnect();
            //Создать Statement
            stmt = conn.createStatement();
            for (String query : sqlStmts) {
                stmt.addBatch(query);
            }
            stmt.executeBatch();

        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                //Закрыть statement
                stmt.close();
            }
            //Закрыть connection
            dbDisconnect();
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            //Подключаемся к базе
            dbConnect();
            System.err.println("Select statement: " + queryStmt +"\n");

            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            e.printStackTrace();
        }finally {
            if (resultSet != null) {
                //Закрыть resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Закрыть Statement
                stmt.close();
            }
            //Закрыть connection
            dbDisconnect();
        }
        return crs;
    }

    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            //Подключаемся к базе
            dbConnect();
            //Создать Statement
            stmt = conn.createStatement();
            //Запустить executeUpdate метод с заданным sql запросом
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Закрыть statement
                stmt.close();
            }
            //Закрыть connection
            dbDisconnect();
        }
    }
}
