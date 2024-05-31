package BuisnessLayer;

import java.util.Date;

public abstract class Employee {
    //varriables
    private int employeeID;
    private String employeeName;
    private String branch;
    private String bankAccount;
    private int salary;
    private Date startDate;
    private Date resignationDate;
    private int vacationDays;
    private boolean isLoggedIn;
    private String password;

    //constructor


    public Employee(){
        this.salary = 0;
        this.resignationDate = null;
    }
    public Employee(String employeeName,int employeeID, String branch, String bankAccount,int salary,String password) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.branch = branch;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.resignationDate = null;
        this.password = password;
        isLoggedIn=false;
    }

    //getters and setters
    public int getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getBranch() {
        return branch;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public int getSalary() {
        return salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getResignationDate() {
        return resignationDate;
    }

    public int getVacationDays() {
        return vacationDays;
    }
    public boolean isLoggedIn(){return isLoggedIn;}
    public boolean login(String password){
        if (this.password.equals(password)){
            isLoggedIn = true;
            return true;
        }
        return false;
    }
    public void logout(){isLoggedIn=false;}

}
