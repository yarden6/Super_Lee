package BuisnessLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class Shift {

    //variables
    Employee shiftManager;
    Map<Integer,Role> shiftRoles;
    Date date;
    LocalTime startTime;
    LocalTime endTime;
    Period period;

    //methodes
    public boolean contain(Integer id){
       return shiftRoles.containsKey(id);
    }

    public void remove(int employeeID) {
        shiftRoles.remove(employeeID);
    }





    // getters and setters
    public Employee getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(Employee shiftManager) {
        this.shiftManager = shiftManager;
    }

    public Map<Integer, Role> getShiftRoles() {
        return shiftRoles;
    }

    public void setShiftRoles(Map<Integer, Role> shiftRoles) {
        this.shiftRoles = shiftRoles;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }


}
