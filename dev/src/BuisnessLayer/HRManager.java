package BuisnessLayer;

import java.time.LocalTime;
import java.util.*;


public class HRManager extends Employee{
    //variables
    private Map<Integer,ShiftEmployee> allEmployees;
    private Map<Date,Shift> morningSchedule;
    private Map<Date,Shift> eveningSchedule;


    //Methodes
    public String getPref(){
        String s = "" ;
        for (ShiftEmployee e : allEmployees.values()){
            s = s + e.getEmployeeName() + " " + e.getEmployeeID() + " Roles: " + e.getROles() +
                    "\n" + e.getLastPref() ;
        }
        return s;
    }

    public ShiftEmployee hire(String employeeName, int employeeID, String branch, String bankAccount,
                              boolean isFull, int salary,String password) {
        ShiftEmployee employee = new ShiftEmployee(employeeName, employeeID, branch, bankAccount, isFull, salary,password
                ,this.getEmployeeID());
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
        return employee;
    }

    public void addRoleToEmployee(int employeeID, Role role){
        checkEmployee(employeeID);
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.addRole(role);
        }
        else {
            //TODO (throw e)
        }
    }

    public void changeRoleToEmployee(int employeeID, Role oldRole, Role newRole){
        checkEmployee(employeeID);
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.changeRole(oldRole, newRole);
        }
        else {
            //TODO (throw e)
        }
    }

    public void deleteRoleFromEmployee(int employeeID, Role role){
        checkEmployee(employeeID);
        ShiftEmployee employee = allEmployees.get(employeeID);
        if (employee != null){
            employee.removeRole(role);
        }
        else {
            //TODO (throw e)
        }
    }
    public void checkBranch(String employeeBranch){
    if (!employeeBranch.equals(getBranch()))
        throw new IllegalArgumentException("employee not in the same branch as HR");
    }

    public void createShift( ShiftEmployee shiftManager, Map<Integer,Role> shiftRoles,
                              Date date, LocalTime startTime, LocalTime endTime, Period period){
        for(Integer i : shiftRoles.keySet())
            checkEmployee(i);
        checkEmployee(shiftManager.getEmployeeID());
        if (!allEmployees.containsKey(shiftManager.getEmployeeID()))
            throw new IllegalArgumentException("shiftManager dont exist");
        if (shiftManager.getRoles().contains(Role.SHIFTMANAGER))
            throw new IllegalArgumentException(shiftManager.getEmployeeName() + " isnt a manager");
        for (Integer i : shiftRoles.keySet())
            if (!allEmployees.containsKey(i))
                throw new IllegalArgumentException(i + "isnt a employee");
        Shift s = new Shift(date,shiftManager,shiftRoles,startTime,endTime,period);
        if (period == Period.MORNING)
            morningSchedule.put(date,s);
        else
            eveningSchedule.put(date,s);
    }
    public void updateEmployee(ShiftEmployee employee){
        checkEmployee(employee.getEmployeeID());
        allEmployees.put(employee.getEmployeeID(), employee);
    }
    public void checkEmployee(int id){
        if (allEmployees.containsKey(id))
            throw new IllegalArgumentException("employee not part of this HR branch");
    }
//    public Map<ShiftEmployee,Preferences> GetEmployeesPref(){
//        Map<ShiftEmployee,Preferences> m = new HashMap<>();
//        for (ShiftEmployee e : allEmployees.values())
//            m.put(e,e.getLastPref());
//        return m;
//    }
    // getters and setters
    public Map<Integer, ShiftEmployee> getAllEmployees() {
        return allEmployees;
    }

    public Map<Date, Shift> getMorningSchedule() {
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
