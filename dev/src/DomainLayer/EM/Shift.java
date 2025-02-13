package DomainLayer.EM;


import DomainLayer.EM.Repositories.ShiftRolesRepository;
import Library.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Shift {

    //variables
    ShiftEmployee shiftManager;
    Map<Integer,Role> shiftRoles;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    Period period;
    ShiftRolesRepository shiftRolesRepository = ShiftRolesRepository.getInstance();

    // constructor

    public Shift(LocalDate date, ShiftEmployee shiftManager, Map<Integer, Role> shiftRoles, LocalTime startTime,
                 LocalTime endTime, Period period) {
        this.date = date;
        this.shiftManager = shiftManager;
        this.shiftRoles = shiftRoles;
        this.startTime = startTime;
        this.endTime = endTime;
        this.period = period;
    }

    //methodes
    public boolean contain(int id){
       return (shiftRoles.containsKey(id) || id == shiftManager.getID());
    }
    public String rolesToString(){
        String s = "";
        for (Integer id : shiftRoles.keySet())
            s+= "id: " + id + ", Role: " + shiftRoles.get(id) + "\n";
        return s;
    }

    public void setManager(ShiftEmployee shiftManager) {
        this.shiftManager = shiftManager;
    }


    @Override
    public String toString() {
        return "Date: " + date.toString() + " "+ period + "  "
                + "start: " + startTime +" "
                + "end: " + endTime + "  "
                + "shift manager: " + shiftManager.getEmployeeName() + " ";
//                + getAllEmployees();
    }

    public String getRole(int id){
        if (shiftRoles.containsKey(id))
            return shiftRoles.get(id).toString();
        else if (shiftManager.getID() == id)
            return Role.SHIFTMANAGER.toString();
        else
        return "";
    }

    // getters and setters
    public ShiftEmployee getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(ShiftEmployee shiftManager) {
        this.shiftManager = shiftManager;
    }

    public Map<Integer, Role> getShiftRoles() {
        return shiftRoles;
    }

    public void setShiftRoles(Map<Integer, Role> shiftRoles) {
        this.shiftRoles = shiftRoles;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public void addEmployee(ShiftEmployee e, Role role) {
        shiftRoles.put(e.getID(),role);
//        ------------sql-------------
        shiftRolesRepository.add(new Pair(e.getID(),this));
//        ------------sql-------------
    }
    public void remove(int employeeID) {
        shiftRoles.remove(employeeID);
        //------------sql-------------
        shiftRolesRepository.delete(new Pair(employeeID,this));
        //------------sql-------------
    }
}
