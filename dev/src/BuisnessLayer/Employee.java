package BuisnessLayer;

import java.util.Date;

public abstract class Employee {
    private int employeeID;
    private String employeeName;
    private String branch;
    private String bankAccount;
    private int salary;
    private Date startDate;
    private Date resignationDate;
    int vacationDays;



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
}
