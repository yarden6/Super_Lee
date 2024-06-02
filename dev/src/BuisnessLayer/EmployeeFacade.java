package BuisnessLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmployeeFacade {
    Map<Integer,Employee> employees;

    public Map<Integer,Employee> getEmployees() {
        return employees;
    }


    //Shift Employee methods
    public String makePreferences(int employeeId, boolean[][] shifts, Date startDate) {
        if (checkLoggedin(employeeId))
            return "employee not logged in";
        ((ShiftEmployee) employees.get(employeeId)).callPreferences(shifts,startDate);
        return null;
    }

    //HRManager methods
    public String hireEmployee(int HRid,String employeeName, int employeeID, String branch,
                             String bankAccount, boolean isFull, int salary,String password) {
        if(checkLoggedin(HRid))
            return " not logged in";
        HRManager hr = getHRManager(HRid);
        if (employees.containsKey(employeeID)) {
            ShiftEmployee employee = (ShiftEmployee) employees.get(employeeID);
            if (employee.getResignationDate() == null)
                return "employee already exist";
//                throw new IllegalArgumentException("employee already exist");
            else
                return reHire(employee, hr);
        } else {
            HRManager hrManager = getHRManager(HRid);
            ShiftEmployee employee = hrManager.hire(employeeName, employeeID, branch, bankAccount, isFull, salary, password);
            employees.put(employeeID, employee);
            return null;
        }
    }

    private String reHire(ShiftEmployee employee,HRManager hr){
        hr.getAllEmployees().put(employee.getEmployeeID(),employee);
        employees.put(employee.getEmployeeID(),employee);
        return null;
    }

    //
    public String fireEmployee(int empId,int HRid){
        checkLoggedin(HRid);
        if (employees.containsKey(empId)){
            HRManager hrManager= getHRManager(HRid);
            ShiftEmployee employee = hrManager.fire(empId);
            employees.replace(empId,employee);
            return null;
        }
        else {
//            throw new IllegalArgumentException("employee not exist");
            return "employee not exist";
        }
    }

    public String updateEmployee(ShiftEmployee employee,int HRid){
        checkLoggedin(HRid);
        if (!employees.containsKey(employee.getEmployeeID()))
            return "this employee is not exist";
//            throw new IllegalArgumentException(employee.getEmployeeName() + " doesn't exist");
        employees.put(employee.getEmployeeID(), employee);
        HRManager hr = getHRManager(HRid);
        hr.updateEmployee(employee);
        return null;
    }

    public String getPreferences(int hrID){
        HRManager hr = getHRManager(hrID);
        return hr.getPref();
    }


    public String HRSetShift(int hrID,Map<Integer,String> workersRoles,
                            Date date, LocalTime startTime, LocalTime endTime, String period){
        checkLoggedin(hrID);
        HRManager hr = getHRManager(hrID);
        //TODO foreach to all map values//// to check if the employee have the role he is assign to//// create shift if everything is good/////
        //hr.createShift(shiftManager,shiftRoles,date,startTime,endTime,period);
        return null;
    }

    public String addRoleToEmployee(int HRid,int employeeID, String role){
        checkLoggedin(HRid);
        Role role1 = convertStringToRole(role);
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.addRoleToEmployee(employeeID,role1);
            return null;
        }
        else {
            return employeeID + " doesn't exist";
//            throw new IllegalArgumentException(employeeID + " doesn't exist");
        }
    }

    public String changeRoleToEmployee(int HRid, int employeeID, String currRole, String newRole){
        checkLoggedin(HRid);
        Role currRole1 = convertStringToRole(currRole);
        Role newRole1 = convertStringToRole(newRole);
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.changeRoleToEmployee(employeeID,currRole1, newRole1);
            return null;
        }
        else {
            return employeeID + " doesn't exist";
//            throw new IllegalArgumentException(employeeID + " doesn't exist");
        }
    }

    public String deleteRoleFromEmployee(int HRid,int employeeID, String role){
        checkLoggedin(HRid);
        Role role1 = convertStringToRole(role);
        if (employees.containsKey(employeeID)){
        HRManager hrManager = getHRManager(HRid);
        hrManager.deleteRoleFromEmployee(employeeID,role1);
        return null;
        }
        else {
            return employeeID + " doesn't exist";
//            throw new IllegalArgumentException(employeeid + " doesn't exist");
        }
    }

    public boolean checkLoggedin(int empID){
        Employee e = employees.get(empID);
        return e.isLoggedIn();
//            throw new IllegalArgumentException(e.getEmployeeName() + " isn't logged in");
    }
    public Employee login (int id, String password){
        Employee e = employees.get(id);
        if( e!=null && e.login(password))
            return e;
        return null;
    }
    public String showMyShifts( int empID){
        ShiftEmployee s = getShiftEmployee(empID);
        HRManager hr = getHRManager(s.getHRid());
        return hr.showEmpShift(empID);
    }
    private HRManager getHRManager(int id) {
        return (HRManager) employees.get(id);
    }
    private Role convertStringToRole(String roleName) {
        if (roleName == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        try {
            return Role.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }
    }
    private ShiftEmployee getShiftEmployee(int id){
        return (ShiftEmployee) employees.get(id);
    }
}
