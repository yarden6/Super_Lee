package BuisnessLayer;

import java.time.LocalDate;
import java.util.Date;

public abstract class Employee {
    //varriables
    private int employeeID;
    private String employeeName;
    private String branch;
    private String bankAccount;
    private int salary;
    private LocalDate startDate;
    private LocalDate resignationDate;
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
        this.startDate = LocalDate.now();
    }

    //getters and setters
    public int getID() {
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getResignationDate() {
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
    public void setResignationDate(LocalDate resignationDate) {
        this.resignationDate = resignationDate;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
