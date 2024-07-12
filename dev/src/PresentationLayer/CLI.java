package PresentationLayer;


import DataLayer.DBConnection;
import DomainLayer.EM.Role;
import DomainLayer.EM.*;
import DomainLayer.IM.CategoryFacade;
import DomainLayer.IM.Item;
import DomainLayer.IM.ReportFacade;

import java.time.DayOfWeek;
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
    //--------------------------------------------------
    public CategoryFacade cf;
    public ReportFacade rf;
    boolean hasBeenPrintedReports = false;
    DayOfWeek today = LocalDate.now().getDayOfWeek().minus(1);

    public CLI() {
        employeeFacade = new EmployeeFacade();
        now = LocalDate.now();
        cf = new CategoryFacade();
        rf = new ReportFacade(cf);
        cf.loadData();
        logInMenu();
    }

    public void logInMenu() {
        System.out.println("LOGIN:");
        System.out.println("Enter ID:");
        id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Password:");
        password = scanner.nextLine();
        if (id == 12345678 && password.equals("12345678"))
            addHR();
        else {
            Employee employee = employeeFacade.login(id, password);
            if (employee == null) {
                System.out.println("can't log in, please try again");
                logInMenu();
            } else if (employee instanceof ShiftEmployee) {
                if (((ShiftEmployee)employee).getROles().contains(Role.STOREKEEPER.name()))
                    chooseModule();
                else
                    shiftEmployee();
            } else
                hrManager();
        }
    }

    private void chooseModule() {
        while (true){
            System.out.println("StoreKeeper Menu:");
            System.out.println("1. ShiftEmployee Menu");
            System.out.println("2. Inventory Menu ");
            System.out.println("3. LogOut ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> shiftEmployee();
                case 2 -> barAndYardensModule();
                case 3 -> logInMenu();
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void barAndYardensModule() {
        while (true) {

            publishReportWeekly();
            isNewDay();
            System.out.println("\n---INVENTORY MENU---");
            System.out.println("1. Products Menu");
            System.out.println("2. Reports Menu");
            System.out.println("3. Categories Menu");
            System.out.println("4. Items Menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> productsMenu();
                case 2 -> reportsMenu();
                case 3 -> categoryMenu();
                case 4 -> itemsMenu();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void categoryMenu() {
        while (true) {
            System.out.println("\n---CATEGORY MENU---");
            System.out.println("1. Add category");
            System.out.println("2. Add sub-category");
            System.out.println("3. Add sub-sub-category");
            System.out.println("4. Apply category discount");
            System.out.println("5. View existing categories");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCategory();
                case 2 -> addSubCategory();
                case 3 -> addSubSubCategory();
                case 4 -> applyCategoryDiscount();
                case 5 -> viewExistingCategories();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void reportsMenu() {
        while (true) {
            System.out.println("\n---REPORT MENU---");
            System.out.println("1. View inventory report");
            System.out.println("2. View defective report");
            System.out.println("3. View getting out of stock report");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> inventoryReport();
                case 2 -> defectiveReport();
                case 3 -> OutOfStockReport();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void productsMenu() {
        while (true) {
            System.out.println("\n---PRODUCTS MENU---");
            System.out.println("1. Add product");
            System.out.println("2. View product");
            System.out.println("3. Change supplier name to the cheapest supplier");
            System.out.println("4. Restock store");
            System.out.println("5. Apply product discount");
            System.out.println("6. Update store after purchase");
            System.out.println("7. Locate defective items (if exists)");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProduct();
                case 3 -> setSupplier();
                case 4 -> restockStore();
                case 5 -> applyProductDiscount();
                case 6 -> updateStoreAfterPurchase();
                case 7 -> checkDefective();
                case 8 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void itemsMenu() {
        while (true) {
            System.out.println("\n---ITEMS MENU---");
            System.out.println("1. Add items of a specific product");
            System.out.println("2. Report a defective item");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addItems(-1);
                case 2 -> reportDefectiveItem();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void addProduct() {
        System.out.print("Enter Category to add the product (Main Category Name,Sub-Category Name,Sub-Sub-Category Name): ");
        String categoriesName = scanner.nextLine();
        String[] categories = categoriesName.split(",");
        if (categories.length != 3) {
            System.out.println("Invalid input, please try again. ");
            addProduct();
            return;
        }

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter product location (aisle number): ");
        int aisle = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter producer name: ");
        String producerName = scanner.nextLine();

        System.out.print("Enter selling price: ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter delivery days: ");
        int deliveryDays = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter minimum amount: ");
        int minimumAmount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter cheapest supplier name: ");
        String supplierName = scanner.nextLine();

        boolean succeed = cf.addProduct(categories, name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount,supplierName);
        if (succeed) {
            System.out.print("Enter 1 to add items of this product or 0 otherwise: ");
            int ans = scanner.nextInt();
            if (ans == 1) addItems(MKT);
        }
        else {
            System.out.println("Failed to add");
        }

    }

    private void viewProduct() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.println(cf.viewProduct(MKT));
    }

    private void applyProductDiscount() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter discount: ");
        int discount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter date for the discount to end (yyyy-mm-dd): ");
        String discountDate = scanner.nextLine();
        cf.applyProductDiscount(MKT, discount, discountDate);
    }

    private void setSupplier() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter cheapest supplier name: ");
        String supplierName = scanner.nextLine();
        cf.setSupplier(MKT, supplierName);
    }

    private void restockStore() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter number of items to move to the store: ");
        int numItems = scanner.nextInt();
        scanner.nextLine();
        System.out.println(cf.restockStore(MKT, numItems));
    }


    private void updateStoreAfterPurchase() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter ID of items that have been purchased: (itemID1,itemId2,...)");
        String itemsIDs = scanner.nextLine();
        String[] ids = itemsIDs.split(",");
        System.out.println(cf.updateStoreAfterPurchase(MKT, ids));
    }


    private void checkDefective() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.println(cf.checkDefective(MKT));
    }


    // Item
    private void addItems(int MKT) {
        if (MKT == -1) {
            System.out.print("Enter product MKT: ");
            MKT = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.print("Enter identical number of items to add: ");
        int numItems = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter expiration date (yyyy-mm-dd): ");
        String ExpDate = scanner.nextLine();

        System.out.print("Enter buying price: ");
        double buyingPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter buying price after discount (or buying price again if there is no discount): ");
        double buyingDiscount = scanner.nextDouble();
        scanner.nextLine();

        System.out.println(cf.addItems(MKT, numItems, ExpDate, buyingPrice, buyingDiscount));

    }

    private void reportDefectiveItem() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Item id: ");
        int itemID = scanner.nextInt();
        scanner.nextLine();

        System.out.print(cf.reportDefectiveItem(MKT, itemID));
    }


    // Category
    private void applyCategoryDiscount() {
        System.out.print("Enter Category to apply a discount (Main Category Name,Sub-Category Name,Sub-Sub-Category Name): ");
        String categoriesName = scanner.nextLine();
        String[] categories = categoriesName.split(",");
        if (categories.length > 3) {
            System.out.println("Invalid input, please try again. ");
            applyCategoryDiscount();
            return;
        }
        System.out.print("Enter discount: ");
        int discount = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter date for the discount to end (yyyy-mm-dd): ");
        String discountDate = scanner.nextLine();
        System.out.println(cf.applyCategoryDiscount(categories, discount, discountDate));
    }

    private void addSubSubCategory() {
        System.out.print("Enter Main Category Name,Sub-Category Name,Sub-Sub-Category Name:");
        String CategoriesName = scanner.nextLine();
        String[] categories = CategoriesName.split(",");
        if (categories.length == 3 && cf.addSubSubCategory(categories[0], categories[1], categories[2]))
            System.out.println("Succeed");
        else {
            System.out.println("Try again");
            addSubSubCategory();
            return;
        }

    }

    private void addSubCategory() {
        System.out.print("Enter Main Category Name,Sub-Category Name: ");
        String CategoriesName = scanner.nextLine();
        String[] categories = CategoriesName.split(",");
        if (categories.length == 2 && cf.addSubCategory(categories[0], categories[1]))
            System.out.println("Succeed");
        else {
            System.out.println("Try again");
            addSubCategory();
            return;
        }
    }

    private void addCategory() {
        System.out.print("Enter Category Name: ");
        String categoryName = scanner.nextLine();
        if (cf.addCategory(categoryName))
            System.out.println("Succeed");

    }

    private void viewExistingCategories() {
        System.out.println(cf.viewExistingCategories());
    }



    // Report
    private void defectiveReport() {
        System.out.println(rf.makeDefectiveReport());
    }
    private void OutOfStockReport() {
        System.out.println(rf.makeOutOfStockReport());
    }

    private void inventoryReport() {
        System.out.print("Enter Main Categories to present in the inventory report (MainCategoryName1,MainCategoryName2,...) : ");
        String categoriesName = scanner.nextLine();
        String[] categories = categoriesName.split(",");
        System.out.println(rf.makeInventoryReport(categories));
    }

    private void publishReportWeekly() {
        LocalDate current = LocalDate.now();
        if (!hasBeenPrintedReports) {
            String[] reports = rf.publishReportWeekly();
            if (reports != null) {
                System.out.println("Weekly Inventory Report: ");
                System.out.println(reports[0]);
                System.out.println("Weekly Defective Report: ");
                System.out.println(reports[1]);
                hasBeenPrintedReports = true;
            }
        } else if (hasBeenPrintedReports && current.getDayOfWeek() != DayOfWeek.SUNDAY) {
            hasBeenPrintedReports = false;
        }
    }


    private void publishDailyExpirationAlerts() {
        HashMap<Integer,List<Item>> expiredItems = cf.checkExpiration();
        if (!expiredItems.isEmpty()) {
            System.out.println("The system found items that are expired today.");
            System.out.println("Please remove the next items from the store: ");
            for (Integer mkt : expiredItems.keySet()) {
                if (!expiredItems.get(mkt).isEmpty()){
                    System.out.println("Product MKT: " + mkt);
                    for (Item itemToRemove : expiredItems.get(mkt)){
                        System.out.println("   Item ID: " + itemToRemove.getItemId());
                    }
                }

            }
        }
    }

    private void isNewDay() {
        if (!today.equals(LocalDate.now().getDayOfWeek())) { // meaning a day had passed
            publishDailyExpirationAlerts();
            today = LocalDate.now().getDayOfWeek(); // update current day
        }
    }

    private void addHR() {
        System.out.println("Enter the new HR details");
        System.out.println("ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("Branch:");
        String branch = scanner.nextLine();
        System.out.println("Bank Account:");
        String bankAccount = scanner.nextLine();
        System.out.println("SALARY:");
        int salary = scanner.nextInt();
        scanner.nextLine();
        System.out.println("PASSWORD:");
        String employeePassword = scanner.nextLine();
        String ret = employeeFacade.AddHrManager(id, name, branch, bankAccount, salary, employeePassword);
        if (ret != null) {
            System.out.println(ret);
        }
        logInMenu();
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

        switch (choice) {
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
        while (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("Select Employee Data to change:");
            System.out.println("1. Salary");
            System.out.println("2. Bank Account");
            System.out.println("3. Vacation Days");
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        switch (choice) {
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
        System.out.println("ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new Vacation Days:");
        int vacationDays = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.changeVacationDays(employeeID, vacationDays);
        if (ret != null) {
            System.out.println(ret);
            changeVacationDays();
        }
        hrManager();

    }

    private void changeBankAccount() {
        System.out.println("ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new Bank Account:");
        String bankAccount = scanner.nextLine();
        String ret = employeeFacade.changeBankAccount(employeeID, bankAccount);
        if (ret != null) {
            System.out.println(ret);
            changeBankAccount();
        }
        hrManager();
    }

    private void changeSalary() {
        System.out.println("ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new Salary:");
        int salary = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.changeSalary(employeeID, salary);
        if (ret != null) {
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
        while (choice != 1 && choice != 2) {
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
        while (3 < role | role < 1) {
            System.out.println("Choose again, no such role");
            role = scanner.nextInt();
            scanner.nextLine();
        }
        String stringRole = roles[role - 1];
        String[] vehicles = {"A", "B", "C"};
        System.out.println("select driver license:");
        System.out.println("1. A");
        System.out.println("2. B");
        System.out.println("3. C");
        int license = scanner.nextInt();
        scanner.nextLine();
        while (3 < license | license < 1) {
            System.out.println("Choose again, invalid choice");
            license = scanner.nextInt();
            scanner.nextLine();
        }
        String stringLicense = vehicles[license - 1];
        String ret = employeeFacade.hireEmployee(id, name, employeeID, bankAccount, isFull, salary, employeePassword, stringRole, stringLicense);
        if (ret != null) {
            System.out.println(ret);
        }
        hrManager();
    }

    private void fire() {
        System.out.println("Enter Employee to fire");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        String ret = employeeFacade.fireEmployee(employeeID, id);
        if (!ret.isEmpty()) {
            System.out.println(ret);
        }
        hrManager();
    }

    private void setShifts() {
        String pref = employeeFacade.getPreferences(id);
        String stringDate = "";
        while (!isValidDate(stringDate, "^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")) {
            System.out.println("Enter the date of the shift (in format of dd-mm-yyyy)");
            stringDate = scanner.nextLine();
        }
        LocalDate date = convertStringToDate(stringDate);
        if (date.isBefore(now)) {
            System.out.println("you can't set shifts to the past, choose again");
            setShifts();
        }
        if (date.getDayOfYear() / 7 > now.getDayOfYear() / 7 + 1 && !(date.getDayOfYear() < 8 && date.getYear() - now.getYear() == 1)) {
            System.out.println("you can only set shift for this week and the next one");
            setShifts();
        }
        if (date.getDayOfWeek().getValue() == 7) {
            System.out.println("In our religion we don't work on Shabbat, choose again");
        }
        System.out.println(pref);
        System.out.println("Deliveries :");
        System.out.println(employeeFacade.getAllDeliveries());
        createSingleShift(date);
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
        morningWorkersRoles.put(shiftManagerID, "SHIFTMANAGER");
        for (int k = 0; k < shiftWorkers - 1; k++) {
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
            while (3 < role | role < 1) {
                System.out.println("Choose again, no such role");
                role = scanner.nextInt();
                scanner.nextLine();
            }
            morningWorkersRoles.put(employeeID, roles[role - 1]);
        }
        String res = employeeFacade.CheckForDeliveries(date, start, switchTime, true, morningWorkersRoles);
        if (res != null) {
            System.out.println("shift cant be place because: " + res + ", please create this shift again");
            hrManager();
        }
        res = employeeFacade.HRSetShift(id, shiftManagerID, morningWorkersRoles, date, start, switchTime, "MORNING");
        if (res != null) {
            System.out.println("shift cant be place because: " + res + ", please create this shift again");
            hrManager();
        }
        System.out.println("Create the Evening " + " shift of " + date.getDayOfWeek());
        shiftWorkers = getNumberOfWorkers();
        System.out.println("Select manager ID for the Shift");
        shiftManagerID = checkIfEmplExist();
        eveningWorkersRoles.put(shiftManagerID, "SHIFTMANAGER");
        for (int k = 0; k < shiftWorkers - 1; k++) {
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
            while (3 < role | role < 1) {
                System.out.println("Choose again, no such role");
                role = scanner.nextInt();
                scanner.nextLine();
            }
            eveningWorkersRoles.put(employeeID, roles[role - 1]);
        }
        res = employeeFacade.CheckForDeliveries(date, switchTime, end, false, eveningWorkersRoles);
        if (res != null) {
            System.out.println("shift cant be place because: " + res + ", please create this shift again");
            hrManager();
        }
        res = employeeFacade.HRSetShift(id, shiftManagerID, eveningWorkersRoles, date, switchTime, end, "EVENING");
        if (res != null) {
            System.out.println("shift cant be place because: " + res + ", please create this shift again");
            hrManager();
        }
    }

    private int checkIfEmplExist() {
        int shiftEmployeeID = scanner.nextInt();
        scanner.nextLine();
        if (!employeeFacade.shiftEmployeeExist(shiftEmployeeID)) {
            System.out.println(shiftEmployeeID + " is not exist, choose the correct ID");
            return checkIfEmplExist();
        } else if (!employeeFacade.employeeExistInBranch(id, shiftEmployeeID)) {
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
        if (start.isAfter(end) || start.isAfter(switchTime) || switchTime.isAfter(end)) {
            System.out.println("shift can't end before its start, choose hours again");
            return getHours();
        }
        return new LocalTime[]{start, switchTime, end};
    }

    private LocalTime convertToLocalTime(int hour) {
        while (hour < 0 || hour > 23) {
            System.out.println("Hour must be between 0 and 23");
            hour = scanner.nextInt();
            scanner.nextLine();
        }
        return LocalTime.of(hour, 0); // hour: given hour, minute: 0
    }

    private LocalDate convertStringToDate(String s) {//only works for dd-mm-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(s, formatter);
    }

    private void getShifts() {
        System.out.println("Select shifts from:");
        String stringDate = "";
        while (!isValidDate(stringDate, "^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")) {
            System.out.println("Enter the date of this week's sunday (in format of dd-mm-yyyy");
            stringDate = scanner.nextLine();
        }
        System.out.println(employeeFacade.getAllShifts(id, convertStringToDate(stringDate)));
        hrManager();
    }

    private void addRole() {
        String role = "";
        int choice;
        System.out.println("Enter employee ID:");
        int employeeID = scanner.nextInt();
        scanner.nextLine();
        choice = 0;
        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("Choose Role To Add:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        switch (choice) {
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
        if (ret != null) {
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
        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("Choose Role To Change:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        switch (choice) {
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
        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("To What Role:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        switch (choice) {
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
        if (ret != null) {
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
        while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
            System.out.println("Choose Role To remove:");
            System.out.println("1. CASHIER");
            System.out.println("2. SHIFTMANAGER");
            System.out.println("3. DELIVERYGUY");
            System.out.println("4. STOREKEEPER");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        switch (choice) {
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
        if (ret != null) {
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
        boolean[][] shifts = new boolean[6][2];
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < 6; i++) {
            int choice = 0;
            while (choice < 1 || choice > 4) {
                System.out.println("please Choose your shifts for this week:(for each day choose 1 for morning, 2 for evening and 3 for both and 4 for none)");
                System.out.println(days[i] + ":");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            switch (choice) {
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
        String ret = employeeFacade.makePreferences(id, shifts, (LocalDate.now().getDayOfYear() / 7 + 1) % 52);
        if (ret != null) {
            System.out.println(ret);
            setPreferences();
        }
        shiftEmployee();

    }

    private void getShift() {
        System.out.println("Select shifts from:");
        String stringDate = "";
        while (!isValidDate(stringDate, "^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$")) {
            System.out.println("Enter the date of this week's sunday (in format of dd-mm-yyyy");
            stringDate = scanner.nextLine();
        }
        System.out.println(employeeFacade.getShifts(id, convertStringToDate(stringDate)));
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








