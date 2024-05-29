package BuisnessLayer;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class HRManager extends Employee{
    //variables
    private ArrayList<ShiftEmployee> allEmployees;
    private Map<Date,Shift[]> schedule;
    private Map<Integer,ArrayList<Preferences>> historyPref;



    //Methodes
    public void hire( ShiftEmployee e)
    {
        allEmployees.add(e);

    }
    public void fire(ShiftEmployee e){
        allEmployees.remove(e);
        for (Shift [] s : schedule.values()){
            if(s[0].contain(e.getEmployeeID()))
                s[0].remove(e.getEmployeeID());
        }
    }



    // getters and setters
    public ArrayList<ShiftEmployee> getAllEmployees() {
        return allEmployees;
    }

    public void setAllEmployees(ArrayList<ShiftEmployee> allEmployees) {
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
