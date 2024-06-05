package BuisnessLayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


public class HRManager extends Employee{
    //variables
    private Map<Integer,ShiftEmployee> allEmployees;
    private Map<LocalDate,Shift> morningSchedule;
    private Map<LocalDate,Shift> eveningSchedule;

    public HRManager(String employeeName, int employeeID, String branch, String bankAccount, int salary, String password) {
        super(employeeName, employeeID, branch, bankAccount, salary, password);
        allEmployees = new HashMap<>();
        morningSchedule = new HashMap<>();
        eveningSchedule = new HashMap<>();
    }

    //Methodes
    public String getPref(){
        String s = "" ;
        for (ShiftEmployee e : allEmployees.values()){
            s = s + e.getEmployeeName() + " " + e.getID() + " Roles: " + e.getROles() +
                    "\n" + e.getLastPref() ;
        }
        return s;
    }

    public ShiftEmployee hire(String employeeName, int employeeID, String branch, String bankAccount,
                              boolean isFull, int salary,String password,Role role){
        ShiftEmployee employee = new ShiftEmployee(employeeName, employeeID, branch, bankAccount, isFull, salary,password
                ,this.getID(),role);
        allEmployees.put(employeeID, employee);
        return employee;
    }

    public String fire(int id){
        String ans = "";
        if (!checkEmployee(id))
            return id + " not exist";
        ShiftEmployee employee = allEmployees.remove(id);
        for (Shift s : morningSchedule.values()){
            if (s.contain(id) && s.date.isAfter(LocalDate.now())) {
                Role role = s.shiftRoles.get(id);
                s.remove(id);
                return setShiftReplacement(s, role) + " instead of " + id + "\n";
            }
        }
        for (Shift s : eveningSchedule.values()) {
            if (s.contain(id) && s.date.isAfter(LocalDate.now())) {
                Role role = s.shiftRoles.get(id);
                s.remove(id);
                return setShiftReplacement(s, role) + " instead of " + id + "\n";
            }
        }
        employee.setResignationDate(LocalDate.now());
        return ans;
    }

    private String setShiftReplacement(Shift s,Role role) {
        for (ShiftEmployee e : allEmployees.values()){
            if (e.getRoles().contains(role) && e.getPreferences().peek().getShifts()[s.getDate().getDayOfWeek().getValue()-1][s.getPeriod().ordinal()] &&
                    !s.contain(e.getID())){
                s.addEmployee(e,role);
                return "assigned " + e.getEmployeeName() + " (id: " + e.getID() +")"+ " to " + s.getDate() + " " + s.getPeriod() + " as " + role;
            }
        }
        return "no replacement found for " + s.getDate() + " " + s.getPeriod() + " as " + role;
    }

    public String addRoleToEmployee(int employeeID, Role role){
        if (!checkEmployee(employeeID)){
            return "employee not exist";
        }
        ShiftEmployee employee = allEmployees.get(employeeID);
        return employee.addRole(role);
    }

    public String changeRoleToEmployee(int employeeID, Role oldRole, Role newRole){
        if (!checkEmployee(employeeID)){
            return "employee not exist";
        }
        ShiftEmployee employee = allEmployees.get(employeeID);
        return  employee.changeRole(oldRole, newRole);

    }

    public String deleteRoleFromEmployee(int employeeID, Role role){
        if (!checkEmployee(employeeID)){
            return "employee not exist";
        }
        ShiftEmployee employee = allEmployees.get(employeeID);
           return employee.removeRole(role);
    }


    public String createShift(ShiftEmployee shiftManager, Map<Integer,Role> shiftRoles,
                              LocalDate date, LocalTime startTime, LocalTime endTime, Period period){
        for(Integer i : shiftRoles.keySet())
            if (!checkEmployee(i))
                return i + " not exist";
        if (!checkEmployee(shiftManager.getID()))
            return "shift manager not exist ";
        if (!shiftManager.getRoles().contains(Role.SHIFTMANAGER))
            return shiftManager.getEmployeeName() + " isn't a shift manager";
        if (!shiftManager.getPreferences().peek().getShifts()[(date.getDayOfWeek().getValue()) % 7][period.ordinal()])
            return shiftManager.getEmployeeName() + " cant work this shift";
        for (Integer id : shiftRoles.keySet()){
            Role role = shiftRoles.get(id);
            ShiftEmployee employee = allEmployees.get(id);
            if (!employee.getRoles().contains(role))
                return id + " was set to be " + role + " and dont have qualification for it";
            if (!employee.getPreferences().peek().getShifts()[(date.getDayOfWeek().getValue()) % 7][period.ordinal()])
                return id + " cant work this shift";
        }
        Shift s = new Shift(date,shiftManager,shiftRoles,startTime,endTime,period);
        if (period == Period.MORNING)
            morningSchedule.put(date,s);
        else
            eveningSchedule.put(date,s);
        return null;
    }
    public String updateEmployee(ShiftEmployee employee){
        if (!checkEmployee(employee.getID()))
            return "Employee not exist";
        allEmployees.put(employee.getID(), employee);
        return null;
    }
    public boolean checkEmployee(int id){
        return allEmployees.containsKey(id);
    }


    // getters and setters
    public Map<Integer, ShiftEmployee> getAllEmployees() {
        return allEmployees;
    }

    public Map<LocalDate, Shift> getMorningSchedule() {
        return morningSchedule;
    }

    public void addEmployee(ShiftEmployee employee){
        allEmployees.put(employee.getID(), employee);
    }

    public String getEmployeeShifts(int id, LocalDate date) {
        Shift shift = null;
        String s = "";
        for (Map.Entry<LocalDate,Shift> entry : morningSchedule.entrySet()){
            if (entry.getKey().compareTo(date) >=0  && entry.getValue().contain(id))
                s+= entry.getValue().toString() +"| Role: " + entry.getValue().getRole(id) + "\n";
        }
        for (Map.Entry<LocalDate,Shift> entry : eveningSchedule.entrySet()){
            if (entry.getKey().compareTo(date) >=0  && entry.getValue().contain(id) )
                s+= entry.getValue().toString() +"| Role: " + entry.getValue().getRole(id) + "\n";
        }
        if (s.equals("")){
            return "no shifts found";
        }
        return s;
    }

    public String getAllShifts(LocalDate date) {
        Shift shift = null;
        String s = "";
        for (Map.Entry<LocalDate,Shift> entry : morningSchedule.entrySet()){
            if (entry.getKey().compareTo(date) >= 0 )
                s+= entry.getValue().toString() + "\n" + entry.getValue().rolesToString() + "\n";
        }
        for (Map.Entry<LocalDate,Shift> entry : eveningSchedule.entrySet()){
            if (entry.getKey().compareTo(date) >= 0  )
                s+= entry.getValue().toString() + "\n" + entry.getValue().rolesToString() + "\n";
        }
        if (s.isEmpty()){
            return "no shifts found";
        }
        return s;
    }
}
