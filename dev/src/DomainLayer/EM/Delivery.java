package DomainLayer.EM;

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

    public Delivery() {
    }

    public Delivery(LocalDate date, LocalTime hour, Vehicle vehicle) {
        this.date = date;
        this.hour = hour;
        this.vehicle = vehicle;
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
