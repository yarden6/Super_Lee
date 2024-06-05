package PresentationLayer;

import domain.CategoryFacade;
import domain.Item;
import domain.ReportFacade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InventoryMenu {
    public static Scanner scanner = new Scanner(System.in);
    public CategoryFacade cf;
    public ReportFacade rf;
    boolean hasBeenPrintedReports = false;

    boolean isLoadData = false;
    DayOfWeek today = LocalDate.now().getDayOfWeek().minus(1);

    public static void main(String[] args) {
        InventoryMenu inventoryMenu = new InventoryMenu();
    }

    public InventoryMenu() {
        cf = new CategoryFacade();
        rf = new ReportFacade(cf);
        startMenu();
    }

    // Menu
    public void startMenu() {
        while (true) {
            publishReportWeekly();
            isNewDay();

            System.out.println("\n---INVENTORY MENU---");
            System.out.println("1. Products Menu");
            System.out.println("2. Reports Menu");
            System.out.println("3. Categories Menu");
            System.out.println("4. Items Menu");
            System.out.println("5. Load Data");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> productsMenu();
                case 2 -> reportsMenu();
                case 3 -> categoryMenu();
                case 4 -> itemsMenu();
                case 5 -> {
                    if (isLoadData) System.out.println("Data is already loaded");
                    else cf.loadData();
                }
                case 6 -> {
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
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> inventoryReport();
                case 2 -> defectiveReport();
                case 3 -> {
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
            System.out.println("3. Restock store");
            System.out.println("4. Apply product discount");
            System.out.println("5. Update store after purchase");
            System.out.println("6. Locate defective items (if exists)");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProduct();
                case 3 -> restockStore();
                case 4 -> applyProductDiscount();
                case 5 -> updateStoreAfterPurchase();
                case 6 -> checkDefective();
                case 7 -> {
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


    // user choices

    // Product
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

        boolean succeed = cf.addProduct(categories, name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount);
        if (succeed) {
            System.out.print("Enter 1 to add items of this product or 0 otherwise: ");
            int ans = scanner.nextInt();
            if (ans == 1) addItems(MKT);
        }
        else {
            System.out.println("This category doesn't exist");
        }

    }

    private void viewProduct() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.print(cf.viewProduct(MKT));
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
        if (!cf.updateStoreAfterPurchase(MKT, ids)) {
            System.out.println("There is no such a MKT");
        } else
            System.out.println("Succeed");

    }


    private void checkDefective() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        System.out.println(cf.checkDefective(MKT));
    }

//    private void updateProduct() {
//        System.out.print("Enter product MKT: ");
//        int MKT = scanner.nextInt();
//        scanner.nextLine();
//
//        System.out.println("1. Update store amount");
//        System.out.println("2. Update storage amount");
//        System.out.println("3. Update selling price");
//        System.out.println("4. Update delivery days");
//        System.out.println("5. Update minimum amount");
//        System.out.println("6. Update aisle");
//        System.out.println("7. Exit");
//        System.out.print("Enter your choice: ");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                System.out.print("Enter new store amount: ");
//                int storeAmount = scanner.nextInt();
//                scanner.nextLine();
//                cf.setProductStoreAmount(MKT, storeAmount);
//                break;
//            case 2:
//                System.out.print("Enter new storage amount: ");
//                int storageAmount = scanner.nextInt();
//                scanner.nextLine();
//                cf.setProductStorageAmount(MKT, storageAmount);
//                break;
//            case 3:
//                System.out.print("Enter new selling price: ");
//                double sellingPrice = scanner.nextDouble();
//                scanner.nextLine();
//                //product.setSellingPrice(sellingPrice);
//                break;
//            case 4:
//                System.out.print("Enter new delivery days: ");
//                int deliveryDays = scanner.nextInt();
//                scanner.nextLine();
//                //product.setDeliveryDays(deliveryDays);
//                break;
//            case 5:
//                System.out.print("Enter new minimum amount: ");
//                int minAmount = scanner.nextInt();
//                scanner.nextLine();
//                //product.setDeliveryDays(deliveryDays);
//                break;
//            case 6:
//                System.out.print("Enter new aisle: ");
//                int aisle = scanner.nextInt();
//                scanner.nextLine();
//                //product.setSellingPrice(sellingPrice);
//                break;
//            case 7:
//                return;
//            default:
//                System.out.println("Invalid choice, please try again.");
//        }
//    }


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

        cf.reportDefectiveItem(MKT, itemID);
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
        cf.applyCategoryDiscount(categories, discount, discountDate);
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
}
