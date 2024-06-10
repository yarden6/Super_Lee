package PresentationLayer;

import BuisnessLayer.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLI {
    public static Scanner scanner = new Scanner(System.in);
    EmployeeFacade employeeFacade;
    int id;
    String password;
    LocalDate now;
    LocalDate setShiftsTo;

    public CLI(List<HRManager> hrManagers, List<ShiftEmployee> shiftEmployees){
        employeeFacade = new EmployeeFacade(hrManagers, shiftEmployees);
        now = LocalDate.now();
//        setShiftsTo = now;
//        System.out.println(now.getDayOfWeek().getValue());
//        while (setShiftsTo.getDayOfWeek().getValue() != 2 && setShiftsTo.getDayOfWeek().getValue() != 6 ){
//            setShiftsTo = setShiftsTo.plusDays(1);
//        }
//        if (setShiftsTo.getDayOfWeek().getValue() == 6)
//            setShiftsTo = setShiftsTo.minusDays(4);
        logInMenu();
    }
    public void logInMenu()
    {
        System.out.println("LOGIN:");
        System.out.println("Enter ID:");
        id = scanner.nextInt();
        scanner.nextLine();
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
        System.out.println("3. Set daily Shifts");
        System.out.println("4. get all Shifts");
        System.out.println("5. add Employee Role ");
        System.out.println("6. change Employee Role ");
        System.out.println("7. delete Employee Role ");
        System.out.println("8. change Employee Data");
        System.out.println("9. logOut");

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
                changeEmployeeData();
                break;
            case 9:
                employeeFacade.logout(id);
                logInMenu();
            default:
                System.out.println("this employee action is not exist");
                hrManager();
        }
    }

    private void changeEmployeeData() {
        int choice = 0;
        while (choice != 1 && choice != 2 && choice != 3){
            System.out.println("Select Employee Data to change:");
            System.out.println("1. Salary");
            System.out.println("2. Bank Account");
            System.out.println("3. Vacation Days");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        switch (choice){
            case 1:
                changeSalary();
                break;
            case 2:
                changeBankAccount();
                break;
            case 3:
                changeVacationDays();
                break;
            default:
                System.out.println("Invalid choice, please try again");
                changeEmployeeData();
        }

    }

    private void changeVacationDays() {
        System.out.println("Enter the new Vacation Days:");
        int vacationDays = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.changeVacationDays(id, vacationDays);
        if (ret != null){
            System.out.println(ret);
            changeVacationDays();
        }
        hrManager();

    }

    private void changeBankAccount() {
        System.out.println("Enter the new Bank Account:");
        String bankAccount = scanner.nextLine();
        String ret = employeeFacade.changeBankAccount(id, bankAccount);
        if (ret != null){
            System.out.println(ret);
            changeBankAccount();
        }
        hrManager();
    }

    private void changeSalary() {
        System.out.println("Enter the new Salary:");
        int salary = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.changeSalary(id, salary);
        if (ret != null){
            System.out.println(ret);
            changeSalary();
        }
        hrManager();
    }

    private void hire() {
        System.out.println("Enter the new Employee details");
        System.out.println("ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Bank Account:");
        String bankAccount = scanner.nextLine();
        int choice = 0;
        while (choice != 1 && choice!=2){
        System.out.println("Full Job Employee:? 1. YES      2. NO");
        choice = scanner.nextInt();
        scanner.nextLine();
        }
        boolean isFull = switch (choice) {
            case 1 -> true;
            case 2 -> false;
            default -> false;
        };
        System.out.println("SALARY:");
        int salary = scanner.nextInt();
        scanner.nextLine();
        System.out.println("PASSWORD:");
        String employeePassword = scanner.nextLine();
        String[] roles = {"CASHIER", "STOREKEEPER", "DELIVERYGUY"};
        System.out.println("role:");
        System.out.println("1. CASHIER");
        System.out.println("2. STOREKEEPER");
        System.out.println("3. DELIVERYGUY");
        int role = scanner.nextInt();
        scanner.nextLine();
        while (3 < role | role < 1){
            System.out.println("Choose again, no such role");
            role = scanner.nextInt();
            scanner.nextLine();
        }
        String stringRole = roles[role - 1];
        String ret = employeeFacade.hireEmployee(id,name, employeeID, bankAccount, isFull, salary, employeePassword,stringRole);
        if (ret != null){
            System.out.println(ret);
        }
        hrManager();
    }

    private void fire() {
        System.out.println("Enter Employee to fire");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.fireEmployee(employeeID, id);
        if (!ret.isEmpty()){
            System.out.println(ret);
        }
        hrManager();
    }

    private void setShifts() {
        String pref = employeeFacade.getPreferences(id);
        String stringDate = "";
        while (!isValidDate(stringDate,"^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")){
        System.out.println("Enter the date of the shift (in format of dd-mm-yyyy)");
        stringDate = scanner.nextLine();
        }
        LocalDate date = convertStringToDate(stringDate);
        if (date.isBefore(now)){
            System.out.println("you can't set shifts to the past, choose again");
            setShifts();
        }
        if(date.getDayOfYear()/7 >now.getDayOfYear()/7 + 1 && !(date.getDayOfYear() < 8 && date.getYear()-now.getYear()==1 )){
            System.out.println("you can only set shift for this week and the next one");
            setShifts();
        }
        if (date.getDayOfWeek().getValue() == 7){
            System.out.println("In our religion we don't work on Shabbat, choose again");
        }
                System.out.println(pref);
                createSingleShift( date);
            date = date.plusDays(1);
        hrManager();

    }

    private void createSingleShift(LocalDate date) {
        LocalTime start;
        LocalTime switchTime;
        LocalTime end;
        Map<Integer, String> morningWorkersRoles = new HashMap<>();
        Map<Integer, String> eveningWorkersRoles = new HashMap<>();
        int employeeID;
        int role;
        int shiftWorkers;
        int shiftManagerID;
        String[] roles = {"CASHIER", "STOREKEEPER", "DELIVERYGUY"};
        System.out.println("Create the shifts of " + date.getDayOfWeek());
        LocalTime[] shiftsHour = getHours();
        start = shiftsHour[0];
        switchTime = shiftsHour[1];
        end = shiftsHour[2];
        System.out.println("Morning Shift of " + date.getDayOfWeek());
        shiftWorkers = getNumberOfWorkers();
        System.out.println("Select manager ID for the Shift");
        shiftManagerID = checkIfEmplExist();
        for (int k = 0; k < shiftWorkers - 1; k++){
            employeeID = -1;
            System.out.println("Select Employee ID:");
            while (employeeID == -1) {
                employeeID = checkIfEmplExist();
                if (employeeID == shiftManagerID || morningWorkersRoles.containsKey(employeeID)) {
                    System.out.println("Employee cant be twice in the same shift, choose other employee");
                    employeeID = -1;
                }
            }
            System.out.println("Choose Role:");
            System.out.println("1. CASHIER");
            System.out.println("2. STOREKEEPER");
            System.out.println("3. DELIVERYGUY");
            role = scanner.nextInt();
            scanner.nextLine();
            while (3 < role | role < 1){
                System.out.println("Choose again, no such role");
                role = scanner.nextInt();
                scanner.nextLine();
            }
            morningWorkersRoles.put(employeeID, roles[role - 1]);
        }
        String res = employeeFacade.HRSetShift(id,shiftManagerID, morningWorkersRoles, date, start,switchTime,"MORNING");
        if (res != null){
            System.out.println("shift cant be place because: " + res + ", please create this shift again");
            createSingleShift(date);
        }
        System.out.println("Create the Evening " + " shift of " + date.getDayOfWeek());
        shiftWorkers = getNumberOfWorkers();
        System.out.println("Select manager ID for the Shift");
        shiftManagerID = checkIfEmplExist();
        for (int k = 0; k < shiftWorkers - 1; k++){
            employeeID = -1;
            System.out.println("Select Employee ID:");
            while (employeeID == -1) {
                employeeID = checkIfEmplExist();
                if (employeeID == shiftManagerID || eveningWorkersRoles.containsKey(employeeID)) {
                    System.out.println("Employee cant be twice in the same shift, choose other employee");
                    employeeID = -1;
                }
            }
            System.out.println("Choose Role:");
            System.out.println("1. CASHIER");
            System.out.println("2. STOREKEEPER");
            System.out.println("3. DELIVERYGUY");
            role = scanner.nextInt();
            scanner.nextLine();
            while (3 < role | role < 1){
                System.out.println("Choose again, no such role");
                role = scanner.nextInt();
                scanner.nextLine();
            }
            eveningWorkersRoles.put(employeeID, roles[role - 1]);
        }
        String res2 = employeeFacade.HRSetShift(id,shiftManagerID, eveningWorkersRoles, date, switchTime,end,"EVENING");
        if (res2 != null){
            System.out.println("shift cant be place because: " + res2 + ", please create this shift again");
            createSingleShift(date);
        }
    }

    private int checkIfEmplExist() {
        int shiftEmployeeID = scanner.nextInt();
        scanner.nextLine();
        if (!employeeFacade.shiftEmployeeExist(shiftEmployeeID)) {
            System.out.println(shiftEmployeeID + " is not exist, choose the correct ID");
            return checkIfEmplExist();
        }
        else if (!employeeFacade.employeeExistInBranch(id, shiftEmployeeID)) {
            System.out.println(shiftEmployeeID + " is not in the assign to this branch manager, choose the correct ID");
            return checkIfEmplExist();
        }
            return shiftEmployeeID;
    }

    private int getNumberOfWorkers() {
        System.out.println("Select number of employees for the shift: ");
        int shiftWorkers = scanner.nextInt();
        scanner.nextLine();
        int branchWorkers = employeeFacade.shiftTotalEmployees(id);
        if (shiftWorkers > branchWorkers) {
            System.out.println("you set " + shiftWorkers + " to this shift but there are only " + branchWorkers + " in this branch, choose again");
            shiftWorkers = getNumberOfWorkers();
        }
        if (shiftWorkers < 1) {
            System.out.println("shift must have at least 1 workers, choose again");
            shiftWorkers = getNumberOfWorkers();
        }
        return shiftWorkers;
    }

    private LocalTime[] getHours() {
        int time;
        System.out.println("Enter when the morning shift starts (hour between 0-23)");
        time = scanner.nextInt();
        scanner.nextLine();
        LocalTime start = convertToLocalTime(time);
        System.out.println("Enter when switch shift (hour between 0-23)");
        time = scanner.nextInt();
        scanner.nextLine();
        LocalTime switchTime = convertToLocalTime(time);
        System.out.println("Enter when the evening shift ends (hour between 0-23)");
        time = scanner.nextInt();
        scanner.nextLine();
        LocalTime end = convertToLocalTime(time);
        if (start.isAfter(end) || start.isAfter(switchTime) || switchTime.isAfter(end)){
            System.out.println("shift can't end before its start, choose hours again");
            return getHours();
        }
        return new LocalTime[]{start,switchTime,end};
    }

    private LocalTime convertToLocalTime(int hour) {
        while (hour < 0 || hour > 23) {
            System.out.println("Hour must be between 0 and 23");
            hour = scanner.nextInt();
            scanner.nextLine();
        }
        return LocalTime.of(hour, 0); // hour: given hour, minute: 0
    }
    private LocalDate convertStringToDate(String s){//only works for dd-mm-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(s,formatter);
    }

    private void getShifts() {
        System.out.println("Select shifts from:");
        String stringDate = "";
        while (!isValidDate(stringDate,"^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")){
            System.out.println("Enter the date of this week's sunday (in format of dd-mm-yyyy");
            stringDate = scanner.nextLine();
        }
        System.out.println(employeeFacade.getAllShifts(id,convertStringToDate(stringDate)));
        hrManager();
    }

    private void addRole() {
        String role = "";
        int choice;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        choice = 0;
        while ( choice!= 1 && choice!=2 && choice!= 3 && choice!=4 ){
            System.out.println("Choose Role To Add:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

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
        }
        hrManager();
    }

    private void changeRole() {
        String oldRole = null;
        String newRole = null;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();
        int choice = 0;
        while ( choice!= 1 && choice!=2 && choice!= 3 && choice!=4 ){
        System.out.println("Choose Role To Change:");
        System.out.println("1. CASHIER");
        System.out.println("2. SHIFTMANAGER");
        System.out.println("3. DELIVERYGUY");
        System.out.println("4. STOREKEEPER");
        choice = scanner.nextInt();
        scanner.nextLine();
        }

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
        choice = 0;
        while ( choice!= 1 && choice!=2 && choice!= 3 && choice!=4 ){
            System.out.println("To What Role:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

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
        scanner.nextLine();

        choice = 0;
        while ( choice!= 1 && choice!=2 && choice!= 3 && choice!=4 ){
            System.out.println("Choose Role To remove:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

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
        int choice = 0;
        while (choice > 4 || choice < 1) {
            System.out.println("Select Shift Employee Action");
            System.out.println("1. Set preferences");
            System.out.println("2. Get Preferences");
            System.out.println("3. Get Weekly Shifts");
            System.out.println("4. LogOut");

            choice = scanner.nextInt();
            scanner.nextLine();
        }
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
                employeeFacade.logout(id);
                logInMenu();
            default:
                 System.out.println("this employee action is not exist");
                 shiftEmployee();
            }
        
        
    }

    private void setPreferences() {
        boolean [][] shifts = new boolean[6][2];
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < 6; i++)
        {
            int choice = 0;
            while (choice < 1 || choice >4) {
                System.out.println("please Choose your shifts for this week:(for each day choose 1 for morning, 2 for evening and 3 for both and 4 for none)");
                System.out.println(days[i] + ":");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            switch (choice){
                case 1:
                    shifts[i][0] = true;
                    shifts[i][1] = false;
                    break;
                case 2:
                    shifts[i][0] = false;
                    shifts[i][1] = true;
                    break;
                case 3:
                    shifts[i][0] = true;
                    shifts[i][1] = true;
                    break;
                case 4:
                    shifts[i][0] = false;
                    shifts[i][1] = false;
            }
        }
        String ret = employeeFacade.makePreferences(id, shifts, (LocalDate.now().getDayOfYear()/7 + 1)%52);
        if (ret != null){
            System.out.println(ret);
            setPreferences();
        }
        shiftEmployee();

    }

    private void getShift() {
        System.out.println("Select shifts from:");
        String stringDate = "";
        while (!isValidDate(stringDate,"^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")){
            System.out.println("Enter the date of this week's sunday (in format of dd-mm-yyyy");
            stringDate = scanner.nextLine();
        }
        System.out.println(employeeFacade.getShifts(id,convertStringToDate(stringDate)));
        shiftEmployee();
    }

    private void getPreferences() {
        System.out.println(employeeFacade.getLastPref(id));
        shiftEmployee();
    }

    private static boolean isValidDate(String date, String pattern) {
        // Compile the regex pattern
        Pattern compiledPattern = Pattern.compile(pattern);
        // Create a matcher for the input date
        Matcher matcher = compiledPattern.matcher(date);
        // Check if the date matches the pattern
        return matcher.matches();
    }
}








