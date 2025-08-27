//booking DAO - data access object
//used to access data from booking table in database
package BusReservation_JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BookingDAO {
    //to get the total seats booked in the bus on certain date
    public static int getSeatsBooked(int bus_No, Date date) throws SQLException {
        String query = "select count(passenger_name) from booking where bus_no = ? and travel_date = ? and status = 'cancelled'";
        Connection connection = DB_Connection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        // to convert date in java format(data + time) to sql date format(only date)
        //getTime() converts date to milliseconds and it is converted to sql date format
        java.sql.Date sqlFormat_date = new java.sql.Date(date.getTime());
        preparedStatement.setInt(1, bus_No);
        preparedStatement.setDate(2, sqlFormat_date);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        
        return resultSet.getInt(1);
    }
    //to insert booking data into table
    public static void insertBooking(Booking data) throws SQLException{
        String query = "insert into booking(passenger_name, passenger_age, " +
                " contact_number, boarding, destination, travel_date, bus_no, status) " +
                " values(?,?,?,?,?,?,?,?) ";
        Connection connection = DB_Connection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
//        System.out.println(preparedStatement);
        
        preparedStatement.setString(1,data.getName());
        preparedStatement.setInt(2,data.getAge());
        preparedStatement.setString(3, data.getContactNo());
        preparedStatement.setString(4,data.getBoarding());
        preparedStatement.setString(5,data.getDestination());
        
        java.sql.Date sqlFormat_date = new java.sql.Date(data.getDate().getTime());
        preparedStatement.setDate(6,sqlFormat_date);
        
        preparedStatement.setInt(7,data.getBusNo());
        preparedStatement.setString(8,"booked");
        preparedStatement.executeUpdate();
        
        connection.close();
    }
    //to return the booking id of the passenger
    public static int getBookingDetails(Booking data)throws SQLException{
        
        String query = "select passenger_id from booking where passenger_name = ? and " +
                "bus_no = ? and travel_date = ?";
        Connection connection = DB_Connection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        preparedStatement.setString(1, data.getName());
        preparedStatement.setInt(2, data.getBusNo());
        java.sql.Date sqlFormat_date1 = new java.sql.Date(data.getDate().getTime());
        preparedStatement.setDate(3,sqlFormat_date1);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        resultSet.next();
        return resultSet.getInt(1);
    }
    public static int cancelTicket(int passenger_id) throws SQLException{
        String query = "update booking set status = 'cancelled' where passenger_id = ?";
        Connection connection = DB_Connection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        preparedStatement.setInt(1, passenger_id);
        
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected == 0 ){
            System.out.println("No booking was found on this passengerId");
            return 0;
        }
        return rowsAffected;
    }
    
    // to calculate the distance between 2 stops, minimum 150 km
    public static int distanceValidity(Booking data) {
        //        select distinct ((select distance from stops
        //        where stop_name = 'Kallakurchi' and bus_no = 1) -
        //                (select distance from stops
        //        where stop_name = 'Coimbatore' and bus_no = 1)) as distance
        //        from stops
        int distance = 0;
        try {
            String query = " select s1.distance - s2.distance " +
                    " from stops s1 " +
                    " left join stops s2 " +
                    " on s1.bus_no = s2.bus_no  " +
                    " where s1.stop_name = ? " +
                    " and s2.stop_name = ? " +
                    " and s1.bus_no = ? ; ";
            Connection connection = DB_Connection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, data.getDestination());
            preparedStatement.setString(2, data.getBoarding());
            preparedStatement.setInt(3, data.getBusNo());

//            System.out.println(preparedStatement);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                distance = Math.abs(resultSet.getInt(1));
                
                if (distance <= 0) {
                    throw new SQLException("Invalid distance calculated. Please check stop order.");
                }
            } else {
                throw new SQLException("Invalid stops and bus number. Verify input details properly");
            }
        } catch (SQLException s) {
            System.out.println(s);
        }
        return distance;
    }
    
    //function to calculate the amount of ticket
    public static double ticketFare(Booking data, int distance)throws SQLException{
        
        double fare = 0;
        String query = " select fare from bus where bus_no = ? ";
        
        Connection connection = DB_Connection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        preparedStatement.setInt(1, data.getBusNo());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        fare = resultSet.getInt(1);
        int age = data.getAge();
        if((age>=5 && age<=12) || age>=65){
            fare = fare*distance;
            fare = fare - (0.20 * fare);
        }
        return fare;
    }
}
