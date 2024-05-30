package BuisnessLayer;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class HRManager extends Employee{
    //variables
    private Map<Integer,ShiftEmployee> allEmployees;
    private Map<Date,Shift[]> schedule;
    private Map<Integer,ArrayList<Preferences>> historyPref;



    //Methodes
    public ShiftEmployee hire(String employeeName, int employeeID, String branch, String bankAccount, boolean isFull)
    {
        //TODO (HR manager complete all new employee data and create new instance of shift employee
        //      add the employee to the list)
        //        allEmployees.add(e);
        return null;
    }
    public void fire(int id){
        allEmployees.remove(id);
        for (Shift [] s : schedule.values()){
            if(s[0].contain(id))
                s[0].remove(id);
        }
    }

    public void addRoleToEmployee(int employeeID, Role role){
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.addRole(role);
        }
        else {
            //TODO (throw e)
        }
    }

    public void changeRoleToEmployee(int employeeID, Role oldRole, Role newRole){
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.changeRole(oldRole, newRole);
        }
        else {
            //TODO (throw e)
        }
    }

    public void deleteRoleFromEmployee(int employeeID, Role role){
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.removeRole(role);
        }
        else {
            //TODO (throw e)
        }
    }

    public Shift createShift(){
        //TODO create new instance of shift from all employees preferences and add it to the shift map
        //Shift s = new Shift(allEmployees, THIS DATE)
        return new Shift();
    }
    // getters and setters
    public Map<Integer, ShiftEmployee> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(Map<Integer, ShiftEmployee> allEmployees) {
        this.allEmployees = allEmployees;
    }

    public Map<Date, Shift[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<Date, Shift[]> schedule) {
        this.schedule = schedule;
    }

    public Map<Integer, ArrayList<Preferences>> getHistoryPref() {
        return historyPref;
    }

    public void setHistoryPref(Map<Integer, ArrayList<Preferences>> historyPref) {
        this.historyPref = historyPref;
    }
}
