package DomainLayer.EM;

import DomainLayer.EM.Repositories.*;

import java.time.LocalDate;

public abstract class Employee {
    //variables
    private final int employeeID;
    private String employeeName;
    private String branch;
    private String bankAccount;
    private int salary;
    private LocalDate startDate;
    private LocalDate resignationDate;
    private int vacationDays;
    private boolean isLoggedIn;
    private String password;
    private ShiftEmployeeRepository shiftEmployeeRepository = ShiftEmployeeRepository.getInstance();

    //constructor

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

    public Employee(int employeeID, String employeeName, String branch, String bankAccount, int salary,
                    LocalDate startDate, LocalDate resignationDate, int vacationDays, String password) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.branch = branch;
        this.bankAccount = bankAccount;
        this.salary = salary;
        this.startDate = startDate;
        this.resignationDate = resignationDate;
        this.vacationDays = vacationDays;
        this.password = password;
        this.isLoggedIn = false;
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

    public LocalDate getResignationDate() {
        return resignationDate;
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

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public int getVacationDays() {
        return vacationDays;
    }

    public String getPassword() {
        return password;
    }
}
