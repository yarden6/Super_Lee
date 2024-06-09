package BuisnessLayer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class EmployeeFacade {
    Map<Integer,ShiftEmployee> shiftEmployees;
    Map<Integer,HRManager> HRManagers;

    public EmployeeFacade(List<HRManager> hrManagers,List<ShiftEmployee> shiftEmployees){
        this.shiftEmployees = new HashMap<>();
        this.HRManagers = new HashMap<>();
        for(HRManager hr : hrManagers) {
            this.HRManagers.put(hr.getID(), hr);
            for (ShiftEmployee emp: shiftEmployees){
                if (hr.getID() == emp.getHRid())
                    hr.addEmployee(emp);
            }
        }
        for (ShiftEmployee e : shiftEmployees)
            this.shiftEmployees.put(e.getID(),e);
    }


    //Shift Employee methods
    public String makePreferences(int employeeId, boolean[][] shifts, int startDate) {
        if (!checkLoggedin(employeeId))
            return "employee not logged in";
        return shiftEmployees.get(employeeId).callPreferences(shifts,startDate);
    }

    public String getLastPref(int id){
        if (!isShiftEmp(id))
            return "employee not exist";
        if(!checkLoggedin(id))
            return "not logged in";
        ShiftEmployee e = getShiftEmployee(id);
        return "this week preferences : \n" + e.getLastPref(0)
                +"\nnext week preferences : " + "\n" + e.getLastPref(1);
    }


    public String getShifts(int id, LocalDate date){
        if (!isShiftEmp(id) )
            return "Employee not exist";
        if(!checkLoggedin(id))
            return "not logged in";
        ShiftEmployee employee = getShiftEmployee(id);
        HRManager hrManager = getHRManager(employee.getHRid());
        return hrManager.getEmployeeShifts(id, date);
    }

    //HRManager methods

    public String getPreferences(int hrID){
        if (!isHR(hrID) )
            return "not valid HR";
        if(!checkLoggedin(hrID))
            return " not logged in";
        HRManager hr = getHRManager(hrID);
        return hr.getPref();
    }

    public String hireEmployee(int HRid,String employeeName, int employeeID,
                             String bankAccount, boolean isFull, int salary,String password,String role) {
        if (!HRManagers.containsKey(HRid))
            return "HR manager not exist";
        if(!checkLoggedin(HRid))
            return " not logged in";
        HRManager hr = getHRManager(HRid);
        if (isHR(employeeID))
            return employeeName + " is already HR manager";
        if (isShiftEmp(employeeID)) {
            ShiftEmployee employee =  shiftEmployees.get(employeeID);
            if (employee.getResignationDate() == null)
                return "employee already exist";
            else
                return reHire(employee, hr);
        } else {
            HRManager hrManager = getHRManager(HRid);
            ShiftEmployee employee = hrManager.hire(employeeName, employeeID,hrManager.getBranch() ,
                    bankAccount, isFull, salary, password,convertStringToRole(role));
            shiftEmployees.put(employeeID, employee);
            return null;
        }
    }

    private String reHire(ShiftEmployee employee,HRManager hr){
        hr.getAllEmployees().put(employee.getID(),employee);
        shiftEmployees.put(employee.getID(),employee);
        return null;
    }

    //
    public String fireEmployee(int empId,int HRid){
        if (!isHR(HRid) )
            return "not valid HR";
        if(!checkLoggedin(HRid))
            return " not logged in";
        if (shiftEmployees.containsKey(empId)){
            HRManager hrManager= getHRManager(HRid);
            return hrManager.fire(empId);
        }
        else {
//            throw new IllegalArgumentException("employee not exist");
            return "employee not exist";
        }
    }

    public String updateEmployee(ShiftEmployee employee,int HRid){
        if(!checkLoggedin(HRid))
            return " not logged in";
        if (!shiftEmployees.containsKey(employee.getID()))
            return "this employee is not exist";
        shiftEmployees.put(employee.getID(), employee);
        HRManager hr = getHRManager(HRid);
        hr.updateEmployee(employee);
        return null;
    }

    public String HRSetShift(int hrID, int shiftManagerID, Map<Integer,String> workersRoles,
                             LocalDate date, LocalTime startTime, LocalTime endTime, String period){
        if (!isHR(hrID) )
            return "not valid HR";
        if(!checkLoggedin(hrID))
            return " not logged in";
        if (!isShiftEmp(shiftManagerID))
            return "shift manager not exist";
        HRManager hr = getHRManager(hrID);
        Map<Integer, Role> employeesRoles = stringToRole(workersRoles);
        return hr.createShift(getShiftEmployee(shiftManagerID),employeesRoles,date,startTime,endTime,convertStringToPeriod(period));
    }

    private Map<Integer, Role> stringToRole(Map<Integer, String> workersRoles) {
        Map<Integer, Role> employeesRoles = new HashMap<>();
        for (Map.Entry<Integer, String> entey: workersRoles.entrySet()){
            Integer id = entey.getKey();
            Role role = convertStringToRole(entey.getValue());
            employeesRoles.put(id, role);
        }
        return employeesRoles;
    }

    public String addRoleToEmployee(int HRid,int employeeID, String role){
        if (!isHR(HRid) )
            return "not valid HR";
        if(!checkLoggedin(HRid))
            return " not logged in";
        Role role1 = convertStringToRole(role);
        if (shiftEmployees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            return hrManager.addRoleToEmployee(employeeID,role1);
        }
        else {
            return employeeID + " doesn't exist";
        }
    }

    public String changeRoleToEmployee(int HRid, int employeeID, String currRole, String newRole){
        if (!isHR(HRid) )
            return "not valid HR";
        if(!checkLoggedin(HRid))
            return " not logged in";
        Role currRole1 = convertStringToRole(currRole);
        Role newRole1 = convertStringToRole(newRole);
        if (shiftEmployees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            return hrManager.changeRoleToEmployee(employeeID,currRole1, newRole1);
        }
        else {
            return employeeID + " doesn't exist";
        }
    }

    public String getAllShifts(int id,LocalDate date){
        if (!isHR(id) )
            return "not valid HR";
        if(!checkLoggedin(id))
            return " not logged in";
        HRManager hrManager = getHRManager(id);
        return hrManager.getAllShifts(date);
    }
    public String deleteRoleFromEmployee(int HRid,int employeeID, String role){
        if (!isHR(HRid) )
            return "not valid HR";
        if(!checkLoggedin(HRid))
            return " not logged in";
        Role role1 = convertStringToRole(role);
        if (shiftEmployees.containsKey(employeeID)){
        HRManager hrManager = getHRManager(HRid);
        return hrManager.deleteRoleFromEmployee(employeeID,role1);
        }
        else {
            return employeeID + " doesn't exist";
        }
    }

    public boolean checkLoggedin(int empID){
        Employee e = getEmp(empID);
        return e.isLoggedIn();
    }

    public Employee login (int id, String password){
        Employee e = getEmp(id);
        if (e != null &&e.login(password))
            return e;
        return null;
    }
    public void logout(int id){
        Employee e = getEmp(id);
        e.logout();
    }

    public int shiftTotalEmployees(int id){
        HRManager hrManager = getHRManager(id);
        return hrManager.getAllEmployees().keySet().size();
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
    private Period convertStringToPeriod(String roleName) {
        if (roleName == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        try {
            return Period.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }
    }
    private ShiftEmployee getShiftEmployee(int id){
        return shiftEmployees.get(id);
    }
    private HRManager getHRManager(int id) {
        return HRManagers.get(id);
    }
    private boolean isHR(int id){
        return  HRManagers.containsKey(id);
    }
    private boolean isShiftEmp(int id){
        return  shiftEmployees.containsKey(id);
    }
    private Employee getEmp(int id){
        Employee e = shiftEmployees.get(id);
        if (e == null)
            e = HRManagers.get(id);
        return e;
    }

    public boolean employeeExistInBranch(int hrID, int shiftEmpID) {
        HRManager hrManager = getHRManager(hrID);
        return hrManager.checkEmployee(shiftEmpID);
    }

    public boolean shiftEmployeeExist(int shiftEmployeeID) {
        return shiftEmployees.containsKey(shiftEmployeeID);
    }

    public String changeSalary(int id, int salary) {
        if(!checkLoggedin(id))
            return " not logged in";
        ShiftEmployee e = getShiftEmployee(id);
        if (e == null)
            return "employee not exist";
        e.setSalary(salary);
        return null;
    }

    public String changeVacationDays(int id, int vacationDays) {
        if(!checkLoggedin(id))
            return " not logged in";
        ShiftEmployee e = getShiftEmployee(id);
        if (e == null)
            return "employee not exist";
        e.setVacationDays(vacationDays);
        return null;
    }

    public String changeBankAccount(int id, String bankAccount) {
        if(!checkLoggedin(id))
            return " not logged in";
        ShiftEmployee e = getShiftEmployee(id);
        if (e == null)
            return "employee not exist";
        e.setBankAccount(bankAccount);
        return null;
    }
}
