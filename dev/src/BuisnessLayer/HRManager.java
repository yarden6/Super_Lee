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
                              boolean isFull, int salary,String password) {
        ShiftEmployee employee = new ShiftEmployee(employeeName, employeeID, branch, bankAccount, isFull, salary,password
                ,this.getID());
        allEmployees.put(employeeID, employee);
        return employee;
    }

    public ShiftEmployee fire(int id){
        checkEmployee(id);
        ShiftEmployee employee = allEmployees.remove(id);
        for (Shift s : morningSchedule.values()){
                s.remove(id);
        }
        for (Shift s : eveningSchedule.values()){
                s.remove(id);
        }
        employee.setResignationDate(LocalDate.now());
        return employee;
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
                return "Employee not exist";
        if (!checkEmployee(shiftManager.getID()))
            return "shift manager not exist ";
        if (!shiftManager.getRoles().contains(Role.SHIFTMANAGER))
            return shiftManager.getEmployeeName() + " isn't a manager";
        for (Integer id : shiftRoles.keySet()){
            Role role = shiftRoles.get(id);
            ShiftEmployee employee = allEmployees.get(id);
            if (!employee.getRoles().contains(role))
                return id + " was set to be " + role + " and dont have qualification for it";
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
    public String showEmpShift(int id ){
        String s = "";
        for (Shift sh : eveningSchedule.values())
            if (sh.contain(id))
                s = s + sh.toString();
        return s;
    }

}
