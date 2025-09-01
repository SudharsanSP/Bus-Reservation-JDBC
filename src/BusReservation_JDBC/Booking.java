//tp  store & process passenger details and booking details
package BusReservation_JDBC;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Booking {
    private String passengerName;
    private String gender;
    private int age;
    private String contactNo;
    private int busNo;
    private Date date;
    private String boarding;
    private String destination;
    private double fare;
    
    
    Booking() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passenger name");
        passengerName = scanner.nextLine().toLowerCase();
        
        // input the gender
        while(true) {
            try {
                System.out.println("Enter gender (Male or Female)");
                String input = scanner.next();
                input = input.toLowerCase();
                if (!(input.equals("male") || input.equals("female"))) {
                    throw new IllegalArgumentException("Invalid input! Please enter only Male or Female.");
                }
                gender = input.trim();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
        // input the age
        while(true){
            try {
                System.out.println("Enter age: ");
                age = scanner.nextInt();
                scanner.nextLine();
                if(age <= 0 || age > 80){
                    throw new IllegalArgumentException("Enter valid age (1-80)");
                }
                break;
            }catch(IllegalArgumentException e){
                System.out.println(e);
            }
            catch(InputMismatchException e){
                System.out.println("Enter digits only an input");
                scanner.nextLine();
            }
        }
        // input the contact number
        while(true){
            try {
                System.out.println("Enter contact_number");
                contactNo = scanner.nextLine();
                contactNo = contactNo.trim();
                if (contactNo.length() != 10) {
                    throw new IllegalArgumentException("Enter a 10 digit valid number");
                }
                boolean flag = false;
                for (int i = 0; i < 10; i++) {
                    char ch = contactNo.charAt(i);
                    if (!(ch >= 48 && ch <= 57)) {
                        flag = true;
                    }
                }
                if (flag) {
                    throw new IllegalArgumentException("Enter only digits as input");
                }
                char c = contactNo.charAt(0);
                if(c == '0'){
                    throw new IllegalArgumentException("First number not to be zero");
                }
                break;
            }catch(IllegalArgumentException e){
                System.out.println(e);
            }
        }
        
        //input boarding location
        while(true){
            try {
                System.out.println("Enter boarding location");
                boarding = scanner.nextLine();
                boarding = boarding.trim().toLowerCase();
                
                if(boarding.length() == 0 || boarding.equals(null)){
                    throw new IllegalArgumentException("Boarding location can't be null");
                }
                boolean flag = false;
                for(int i=0;i<boarding.length();i++){
                    char ch = boarding.charAt(i);
                    if(!(ch>='a' && ch<='z' || ch>='A' && ch<='Z')){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    throw new IllegalArgumentException("Only alphabets should be entered as input");
                }
                break;
            }catch(IllegalArgumentException e){
                System.out.println(e);
            }
        }
        
        //input boarding location
        while(true){
            try {
                System.out.println("Enter destination location");
                destination = scanner.nextLine();
                destination = destination.trim().toLowerCase();
                
                if(destination.length() == 0 || destination.equals(null)){
                    throw new IllegalArgumentException("destination location can't be null");
                }
                boolean flag = false;
                for(int i=0;i<destination.length();i++){
                    char ch = destination.charAt(i);
                    if(!(ch>='a' && ch<='z' || ch>='A' && ch<='Z')){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    throw new IllegalArgumentException("Only alphabets should be entered as input");
                }
                break;
            }catch(IllegalArgumentException e){
                System.out.println(e);
            }
        }
        //date of travel
        System.out.println("Enter travel date(dd-mm-yyyy)");
        String dateInput = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.parse(dateInput);
        
        //bus number
        while(true) {
            try{
                System.out.println("Enter bus number");
                busNo = scanner.nextInt();
                break;
            }catch(InputMismatchException e){
                System.out.println(e);
            }
        }
    }
    public String getName(){
        return this.passengerName;
    }
    public String getGender() {
        return gender;
    }
    public int getAge() {
        return age;
    }
    public String getContactNo() {
        return contactNo;
    }
    public int getBusNo(){
        return busNo;
    }
    public String getBoarding() {
        return boarding;
    }
    public String getDestination() {
        return destination;
    }
    public Date getDate(){
        return this.date;
    }
    public double getFare() {
        return fare;
    }
    
    public boolean isSeatAvailabe(Booking data) throws SQLException {
        // to get the capacity of selected bus
        BusDAO busdao = new BusDAO();
        int capacity = busdao.getCapacity(busNo);
        
        //to get the to seats booked in the bus on the date
        BookingDAO bookingdao = new BookingDAO();
        int booked = bookingdao.getSeatsBooked(busNo, date);
        
        return booked<capacity?true:false;
    }
    
}
