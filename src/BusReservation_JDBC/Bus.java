//to create template for bus and to store details of all the bus
package BusReservation_JDBC;

import java.time.LocalTime;

public class Bus {
    private int busNo;
    private boolean ac;
    private int capacity;
    private String fromLocation;
    private String toLocation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    
    
    //set bus number and AC
    public int getBusNo(){
        return busNo;
    }
    public void setAc(boolean valid){
        ac = valid;
    }
    public boolean getAc(){
        return ac;
    }
    //get and set capacity
    public void setCapacity(int num){
        capacity = num;
    }
    public int getCapacity(){
        return capacity;
    }
    
    
    
}
