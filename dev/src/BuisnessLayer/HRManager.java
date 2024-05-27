package BuisnessLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class HRManager extends Employee{
    ArrayList<ShiftEmployee> allEmployees;
    Map<Date,Shift[]> schedule;
    Map<Integer,ArrayList<Preferences>> historyPref;

}
