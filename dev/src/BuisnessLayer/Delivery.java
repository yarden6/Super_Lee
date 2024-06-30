package BuisnessLayer;

import java.time.LocalDate;
import java.time.LocalTime;

public class Delivery {
    private LocalDate date;
    private LocalTime hour;
    private Vehicle vehicle;

    @Override
    public String toString() {
        return date.toString() + "  " +  hour.toString() + " vehicle type : " +vehicle.toString();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
