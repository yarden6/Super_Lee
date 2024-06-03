package PresentationLayer;

import BuisnessLayer.Employee;
import BuisnessLayer.EmployeeFacade;
import BuisnessLayer.ShiftEmployee;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        String ret = employeeFacade.hireEmployee(id,name, employeeID, branch, bankAccount, isFull, salary, employeePassword);
        if (ret != null){
            System.out.println(ret);
            hire();
        }
        hrManager();
    }

    private void fire() {
        System.out.println("Enter Employee to fire");
        int employeeID = scanner.nextInt();
        String ret = employeeFacade.fireEmployee(employeeID, id);
        if (ret != null){
            System.out.println(ret);
            fire();
        }
        hrManager();
    }

    private void setShifts() {
        //TODO
        String pref = employeeFacade.getPreferences(id);
        System.out.println(pref);
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] period = {"Morning", "Evening"};
        for (int i = 0; i < days.length; i++){
            for (int j = 0; j < period.length; j++) {
                createSingleShift(days[i],period[j]);
            }
        }
        hrManager();
    }

    private void createSingleShift(String day, String period) {
        int time;
        int shiftWorkers;
        LocalTime start;
        LocalTime end;
        Map<Integer, String> workersRoles = new HashMap<>();
        int shiftManagerID;
        int employeeID;
        int role;
        String[] roles = {"CASHIER", "DELIVERYGUY", "STOREKEEPER"};
        System.out.println("Create the " + period + " shift of " + day);
        System.out.println("Enter when the shift starts (hour between 0-23)");
        time = scanner.nextInt();
        start = convertToLocalTime(time);
        System.out.println("Enter when the shift ends (hour between 0-23)");
        time = scanner.nextInt();
        end = convertToLocalTime(time);
        System.out.println("Select number of employees for the shift: ");
        shiftWorkers = scanner.nextInt();
        System.out.println("Select manager ID for the Shift");
        shiftManagerID = scanner.nextInt();
        for (int k = 0; k < shiftWorkers - 1; k++){
            System.out.println("Select Employee ID:");
            employeeID = scanner.nextInt();
            System.out.println("Choose Role:");
            System.out.println("1. CASHIER");
            System.out.println("2. STOREKEEPER");
            System.out.println("3. DELIVERYGUY");
            role = scanner.nextInt();
            while (2 < role | role < 0){
                System.out.println("Choose again, no such role");
            }
            if (workersRoles.containsKey(employeeID)){
                System.out.println("Employee cant be twice in the same shift, please make the shift again");
                createSingleShift(day, period);
            }
            workersRoles.put(employeeID, roles[role]);
        }
        String res = employeeFacade.HRSetShift(id,shiftManagerID, workersRoles, new Date(), start,end, period);
        if (res != null){
            System.out.println("shift cant be place becuse: " + res + ", please create this shift again");
            createSingleShift(day, period);
        }
    }

    private static LocalTime convertToLocalTime(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        return LocalTime.of(hour, 0); // hour: given hour, minute: 0
    }

    private void getShifts() {
        //TODO

    }

    private void addRole() {
        String role = "";
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
            default:
                System.out.println("Invalid choice, please try again");
                addRole();
        }

        String ret = employeeFacade.addRoleToEmployee(id, employeeID, role);
        if (ret != null){
            System.out.println(ret);
            addRole();
        }
        hrManager();
    }

    private void changeRole() {
        String oldRole = "";
        String newRole = "";
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
            default:
                System.out.println("Invalid choice, please try again");
                changeRole();
        }

        String ret = employeeFacade.changeRoleToEmployee(id, employeeID, oldRole, newRole);
        if (ret != null){
            System.out.println(ret);
            changeRole();
        }
        hrManager();
    }

    private void deleteRole() {
        String role = "";
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
            default:
                System.out.println("Invalid choice, please try again");
                deleteRole();
        }

        String ret = employeeFacade.deleteRoleFromEmployee(id, employeeID, role);
        if (ret != null){
            System.out.println(ret);
            deleteRole();
        }
        hrManager();
    }

    private void shiftEmployee() {
        
        System.out.println("Select Shift Employee Action");
        System.out.println("1. Set preferences");
        System.out.println("2. Get Preferences");
        System.out.println("3. Get Weekly Shifts");
        

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                setPreferences();
                break;
            case 2:
                getPreferences();
                break;
            case 3:
                getShift();
                break;
            case 4:
                 System.out.println("thank for using our system");
                 System.exit(0);
            default:
                 System.out.println("this employee action is not exist");
                 shiftEmployee();
            }
        
        
    }

    private void setPreferences() {
        boolean shifts[][] = new boolean[6][2];
        int choice;
        System.out.println("please Choose you shift for this week:(for each day choose 1 for morning, 2 for evening and 3 for both and 4 for none)");
        System.out.println("Sunday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        System.out.println("Monday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        System.out.println("Tuesday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        System.out.println("Wednesday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        System.out.println("Thursday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        System.out.println("Friday:");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                shifts[0][0] = true;
                shifts[0][1] = false;
                break;
            case 2:
                shifts[0][0] = false;
                shifts[0][1] = true;
                break;
            case 3:
                shifts[0][0] = true;
                shifts[0][1] = true;
                break;
            default:
                shifts[0][0] = false;
                shifts[0][1] = false;
        }
        String ret = employeeFacade.makePreferences(id, shifts, new Date());
        if (ret != null){
            System.out.println(ret);
            setPreferences();
        }
        shiftEmployee();

    }

    private void getShift() {
        //TODO
    }

    private void getPreferences() {
        //TODO
    }

}








