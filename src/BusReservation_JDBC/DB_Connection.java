package BusReservation_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    private static String url = "jdbc:mysql://localhost:3306/bus_ticket_reservation";
    private static String username = "root";
    private static String password = "root";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
}
