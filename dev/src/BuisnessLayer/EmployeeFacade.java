package BuisnessLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmployeeFacade {
    Map<Integer,Employee> employees;

    public Map<Integer,Employee> getEmployees() {
        return employees;
    }


    //Shift Employee methods
    public void makePreferences(int employeeId, boolean[][] shifts, Date startDate) {
        checkLoggedin(employeeId);
        ((ShiftEmployee) employees.get(employeeId)).callPreferences(shifts,startDate);
    }

    //HRManager methods
    public void hireEmployee(int HRid,String employeeName, int employeeID, String branch,
                             String bankAccount, boolean isFull, int salary,String password){
        checkLoggedin(HRid);
        if (employees.containsKey(employeeID)) {
            ShiftEmployee employee = (ShiftEmployee) employees.get(employeeID);
            if (employee.getResignationDate() == null)
                throw new IllegalArgumentException("employee already exist");
            updateEmployee(employee, HRid);
        }
        else{
            HRManager hrManager = getHRManager(HRid);
            ShiftEmployee employee = hrManager.hire(employeeName, employeeID,branch, bankAccount, isFull, salary,password);
            employees.put(employeeID, employee);
        }

    }

    //
    public void fireEmployee(int empId,int HRid){
        checkLoggedin(HRid);
        if (employees.containsKey(empId)){
            HRManager hrManager= getHRManager(HRid);
            ShiftEmployee employee = hrManager.fire(empId);
            employees.replace(empId,employee);
        }
        else {
            throw new IllegalArgumentException("employee not exist");
        }
    }

    public void updateEmployee(ShiftEmployee employee,int HRid){
        checkLoggedin(HRid);
        if (!employees.containsKey(employee.getEmployeeID()))
            throw new IllegalArgumentException(employee.getEmployeeName() + " doesn't exist");
        employees.put(employee.getEmployeeID(), employee);
        HRManager hr = getHRManager(HRid);
        hr.updateEmployee(employee);
    }

    public void HRSetShift(int hrid,ShiftEmployee shiftManager, Map<Integer,Role> shiftRoles,
                            Date date, LocalTime startTime, LocalTime endTime, Period period){
        checkLoggedin(hrid);
        HRManager hr = getHRManager(hrid);
        hr.createShift(shiftManager,shiftRoles,date,startTime,endTime,period);
    }

    public void addRoleToEmployee(int HRid,int employeeID, Role role){
        checkLoggedin(HRid);
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.addRoleToEmployee(employeeID,role);
        }
        else {
            //TODO (throw e)
        }
    }

    public void changeRoleToEmployee(int HRid, int employeeID, Role currRole, Role newRole){
        checkLoggedin(HRid);
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.changeRoleToEmployee(employeeID,currRole, newRole);
        }
        else {
            //TODO (throw e)
        }
    }

    public void deleteRoleFromEmployee(int HRid,int employeeid, Role role){
        checkLoggedin(HRid);
        if (employees.containsKey(employeeid)){
        HRManager hrManager = getHRManager(HRid);
        hrManager.deleteRoleFromEmployee(employeeid,role);
        }
        else {
        //TODO (throw e)
        }
    }

    public void checkLoggedin(int empID){
        Employee e = employees.get(empID);
        if(!e.isLoggedIn())
            throw new IllegalArgumentException(e.getEmployeeName() + " isn't logged in");
    }
    public Employee login (int id, String password){
        Employee e = employees.get(id);
        if( e!=null && employees.get(id).login(password))
            return e;
        return null;
    }
    private HRManager getHRManager(int id) {
        return (HRManager) employees.get(id);
    }

}
