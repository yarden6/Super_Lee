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

    public void hireEmployee(int HRid,String employeeName, int employeeID, String branch, String bankAccount, boolean isFull){
        if (employees.containsKey(employeeID)){}
            //TODO (throw e)
        else{
            HRManager hrManager = getHRManager(HRid);
            ShiftEmployee employee = hrManager.hire(employeeName, employeeID,branch, branch, isFull);
            employees.put(employeeID, employee);
        }

    }

    private HRManager getHRManager(int id) {
        return (HRManager) employees.get(id);
    }

    public void fireEmployee(int empId,int HRid){
        if (employees.containsKey(empId)){
            HRManager hrManager= getHRManager(HRid);
            hrManager.fire(empId);
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

    public void addRoleToEmployee(int HRid,int employeeID, Role role){
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.addRoleToEmployee(employeeID,role);
        }
        else {
            //TODO (throw e)
        }
    }

    public void changeRoleToEmployee(int HRid, int employeeID, Role currRole, Role newRole){
        if (employees.containsKey(employeeID)){
            HRManager hrManager = getHRManager(HRid);
            hrManager.changeRoleToEmployee(employeeID,currRole, newRole);
        }
        else {
            //TODO (throw e)
        }
    }

    public void deleteRoleFromEmployee(int HRid,int employeeid, Role role){
        if (employees.containsKey(employeeid)){
        HRManager hrManager = getHRManager(HRid);
        hrManager.deleteRoleFromEmployee(employeeid,role);
    }
    else {
        //TODO (throw e)
    }
    }

}
