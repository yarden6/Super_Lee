package PresentationLayer;

import BuisnessLayer.Employee;
import BuisnessLayer.EmployeeFacade;
import BuisnessLayer.ShiftEmployee;

import java.util.Scanner;

public class CLI {
    public static Scanner scanner = new Scanner(System.in);
    EmployeeFacade employeeFacade;
    int id;
    String password;


    public void logInMenu() {
        System.out.println("Enter ID:");
        id = scanner.nextInt();
        System.out.println("Enter Password:");
        password = scanner.nextLine();
        Employee employee = employeeFacade.login(id, password);
        if (employee == null) {
            System.out.println("can't log in, please try again");
            logInMenu();
        } else if (employee instanceof ShiftEmployee) {
            shiftEmployee();
        } else
            hrManager();


    }

    private void hrManager() {
        System.out.println("Select HR Manager Action:");
        System.out.println("1. Hire Employee");
        System.out.println("2. Fire Employee");
        System.out.println("3. Set weekly Shifts");
        System.out.println("4. get all Shifts");
        System.out.println("5. add Employee Role ");
        System.out.println("6. change Employee Role ");
        System.out.println("7. delete Employee Role ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                hire();
                break;
            case 2:
                fire();
                break;
            case 3:
                setShifts();
                break;
            case 4:
                getShifts();
                break;
            case 5:
                addRole();
                break;
            case 6:
                changeRole();
                break;
            case 7:
                deleteRole();
                break;
            case 8:
                System.out.println("thank for using our system");
                System.exit(0);
            default:
                System.out.println("this employee action is not exist");
                hrManager();
        }
    }

    private void hire() {
        System.out.println("Enter the new Employee details");
        System.out.println("ID:");
        int employeeID = scanner.nextInt();
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Branch:");
        String branch = scanner.nextLine();
        System.out.println("Bank Account:");
        String bankAccount = scanner.nextLine();
        System.out.println("Full Job Employee:? 1. YES      2. NO");
        int choice = scanner.nextInt();
        boolean isFull = switch (choice) {
            case 1 -> true;
            case 2 -> false;
            default -> false;
        };
        System.out.println("SALARY:");
        int salary = scanner.nextInt();
        System.out.println("PASSWORD:");
        String employeePassword = scanner.nextLine();
        employeeFacade.hireEmployee(id,name, employeeID, branch, bankAccount, isFull, salary, employeePassword);
    }

    private void fire() {
        System.out.println("Enter Employee to fire");
        int employeeID = scanner.nextInt();
        employeeFacade.fireEmployee(employeeID, id);
    }

    private void setShifts() {
        //TODO
    }

    private void getShifts() {
        //TODO
    }

    private void addRole() {
        String role;
        int choice;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();

        System.out.println("Choose Role To Change:");
        System.out.println("1. CASHIER");
        System.out.println("2. SHIFTMANAGER");
        System.out.println("3. DELIVERYGUY");
        System.out.println("4. STOREKEEPER");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                role = "CASHIER";
                break;
            case 2:
                role = "SHIFTMANAGER";
                break;
            case 3:
                role = "DELIVERYGUY";
                break;
            case 4:
                role = "STOREKEEPER";
                break;
        }

        employeeFacade.addRoleToEmployee(id, employeeID, role);
    }

    private void changeRole() {
        String oldRole;
        String newRole;
        int choice;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();

        System.out.println("Choose Role To Change:");
        System.out.println("1. CASHIER");
        System.out.println("2. SHIFTMANAGER");
        System.out.println("3. DELIVERYGUY");
        System.out.println("4. STOREKEEPER");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                oldRole = "CASHIER";
                break;
            case 2:
                oldRole = "SHIFTMANAGER";
                break;
            case 3:
                oldRole = "DELIVERYGUY";
                break;
            case 4:
                oldRole = "STOREKEEPER";
                break;
        }

        System.out.println("Choose New Role:");
        System.out.println("1. CASHIER");
        System.out.println("2. SHIFTMANAGER");
        System.out.println("3. DELIVERYGUY");
        System.out.println("4. STOREKEEPER");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                newRole = "CASHIER";
                break;
            case 2:
                newRole = "SHIFTMANAGER";
                break;
            case 3:
                newRole = "DELIVERYGUY";
                break;
            case 4:
                newRole = "STOREKEEPER";
                break;
        }

        employeeFacade.changeRoleToEmployee(id, employeeID, oldRole, newRole);
    }

    private void deleteRole() {
        String role;
        int choice;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();

        System.out.println("Choose Role To Change:");
        System.out.println("1. CASHIER");
        System.out.println("2. SHIFTMANAGER");
        System.out.println("3. DELIVERYGUY");
        System.out.println("4. STOREKEEPER");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                role = "CASHIER";
                break;
            case 2:
                role = "SHIFTMANAGER";
                break;
            case 3:
                role = "DELIVERYGUY";
                break;
            case 4:
                role = "STOREKEEPER";
                break;
        }

        employeeFacade.deleteRoleFromEmployee(id, employeeID, role);
    }


    private void shiftEmployee() {
        
        System.out.println("Select Shift Employee Action");
        System.out.println("1. Set preferences");
        System.out.println("2. Get Preferences");
        System.out.println("3. Get Weekly Shifts");
        System.out.println("");

        int chosie = scanner.nextInt();
        scanner.nextLine();

        switch (chosie) {
            case 1:
                //setPreferences();
                break;
            case 2:
                //getPreferences();
                break;
            case 3:
                //getShift();
                break;
            case 4:
                 System.out.println("thank for using our system");
                 System.exit(0);
            default:
                 System.out.println("this employee action is not exist");
                 shiftEmployee();
            }
        
        
    }
}








