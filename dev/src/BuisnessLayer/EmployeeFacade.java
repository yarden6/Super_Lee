package BuisnessLayer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EmployeeFacade {
    Map<Integer,Employee> employees;

    public Map<Integer,Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Integer,Employee> employees) {
        this.employees = employees;
    }

    //methods
    public void makePreferences(Employee employee, boolean[][] shifts, Date startDate) {
        if (employee instanceof ShiftEmployee){
            ((ShiftEmployee) employee).callPreferences(shifts, startDate);
        }
        else {
            //TODO (throw e)
        }

    }

    public void hireEmployee(String employeeName, int employeeID, String branch, String bankAccount, boolean isFull){
        if (employees.containsKey(employeeID)){}
            //TODO (throw e)
        else{
            HRManager hrManager = getHRManager();
            ShiftEmployee employee = hrManager.hire(employeeName, employeeID,branch, branch, isFull);
            employees.put(employeeID, employee);
        }

    }

    private HRManager getHRManager() {
        //TODO (search the map or convert to list and search there)
        return null;
    }

    public void fireEmployee(Employee employee){
        if (employees.containsKey(employee.getEmployeeID())){
            HRManager hrManager= getHRManager();
            hrManager.fire((ShiftEmployee) employee);
        }
        else {
            //TODO (throw e)
        }
    }

    public void updateEmployee(Employee employee){
        //מה זה אמור להיות?
    }

    public Shift HRSetShift(){
        return new Shift();
        //TODO
    }

    public void addRoleToEmployee(Employee employee, Role role){
        if (employees.containsKey(employee.getEmployeeID())){
            HRManager hrManager = getHRManager();
            hrManager.addRoleToEmployee(employee.getEmployeeID(),role);
        }
        else {
            //TODO (throw e)
        }
    }

    public void changeRoleToEmployee(Employee employee, Role currRole, Role newRole){
        if (employees.containsKey(employee.getEmployeeID())){
            HRManager hrManager = getHRManager();
            hrManager.changeRoleToEmployee(employee.getEmployeeID(),currRole, newRole);
        }
        else {
            //TODO (throw e)
        }
    }

    public void deleteRoleFromEmployee(Employee employee, Role role){if (employees.containsKey(employee.getEmployeeID())){
        HRManager hrManager = getHRManager();
        hrManager.deleteRoleFromEmployee(employee.getEmployeeID(),role);
    }
    else {
        //TODO (throw e)
    }
    }

}
