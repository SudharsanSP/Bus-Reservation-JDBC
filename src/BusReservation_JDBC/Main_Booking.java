//calss to get input and print results
package BusReservation_JDBC;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main_Booking {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
            BusDAO busdao = new BusDAO();
            System.out.println("Availabe buses and details");
        try {
            busdao.displayBusInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        int userOption = 1;
            while (userOption == 1) {
                System.out.println("Enter 1 to book or 2 to cancel or 3 to stop the process");
                userOption = scanner.nextInt(); // input from user to start booking
                
                try {
                    if (userOption == 1) {
                        System.out.println("Booking started.......");
                        Booking booking_data = new Booking();
//                        System.out.println("TEsting 1");
                        
                        BookingDAO bookingDao = new BookingDAO();
                        int distance = bookingDao.distanceValidity(booking_data);
                        
//                        System.out.println(distance);
//                        System.out.println("TEsting 2");
                        
                        if (distance >= 150) { //check the distance is minimum 150km
                            System.out.println("TEsting 3");
                            if (booking_data.isSeatAvailabe(booking_data)) {
//                                System.out.println("TEsting 4");
                                bookingDao.insertBooking(booking_data);
//                                System.out.println("TEsting 5");
                                System.out.println("Ticket Booking confirmed");
                                System.out.println("Your passenger id is - " + bookingDao.getBookingDetails(booking_data));
//                                System.out.println("TEsting 6");
                                System.out.println("Your ticket price is - "+ bookingDao.ticketFare(booking_data, distance));
//                                System.out.println("TEsting 7");
                            } else {
                                System.out.println("Booking is not available in the date");
                            }
                        }else{
                            System.out.println("Minimum distance between boarding and destination must be greater than 150kn");
                        }
                    } else if (userOption == 2) {
                        System.out.println("Enter your passenger_id");
                        int passenger_id = scanner.nextInt();
                        if (BookingDAO.cancelTicket(passenger_id) == 1) {
                            System.out.println("Ticket cancelled successfully");
                        }
                        userOption = 1;
                    } else if (userOption == 3) {
                        System.out.println("Process has ended");
                        return;
                    }else {
                        throw new IllegalArgumentException("Enter valid input");
                    }
                }catch(InputMismatchException e){
                    System.out.println(e);
                }catch(IllegalArgumentException e){
                    System.out.println(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
