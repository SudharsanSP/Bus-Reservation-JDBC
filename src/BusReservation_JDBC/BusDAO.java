//bus DAO - data access object
//used to access data from bus table in database
package BusReservation_JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Time;

public class BusDAO {
    //to display all the bus information
    public static void displayBusInfo() throws SQLException {
        String query = "select * from bus;";
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        System.out.printf("%-8s %-6s %-12s %-15s %-15s %-15s %-15s%n",
                "Bus No", "AC", "TotalSeats", "Boarding", "Destination", "Departure", "Arrival");
        System.out.println("--------------------------------------------------------------------------------------------");
        
        while (resultSet.next()) {
            int busNo = resultSet.getInt(1);
            int acFlag = resultSet.getInt(2);
            int totalSeats = resultSet.getInt(3);
            String boarding = resultSet.getString(4);
            String destination = resultSet.getString(5);
            Time departure = resultSet.getTime(6);
            Time arrival = resultSet.getTime(7);
            
            String ac = (acFlag == 0) ? "No" : "Yes";
            
            System.out.printf("%-8d %-6s %-12d %-15s %-15s %-15s %-15s%n",
                    busNo, ac, totalSeats, boarding, destination, departure, arrival);
        }
        System.out.println();
        
        connection.close();
    }
    //to get the capacity of the selected bus
    public static int getCapacity(int busNo) throws SQLException{
        String query = "select capacity from bus where bus_no = "+ busNo;
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery(query);
        resultset.next();
        return resultset.getInt(1);
        
    }
}
