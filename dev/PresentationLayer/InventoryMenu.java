package PresentationLayer;

import java.util.Scanner;

public class InventoryMenu {
    public static Scanner scanner = new Scanner(System.in);

    // Menu
    public void startMenu(){
        while (true) {
            System.out.println("---INVENTORY MENU---");
            System.out.println("1. Products Menu");
            System.out.println("2. Reports Menu");
            System.out.println("3. Categories Menu");
            System.out.println("4. Items Menu");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productsMenu();
                    break;
                case 2:
                    reportsMenu();
                    break;
                case 3:
                    categoryMenu();
                    break;
                case 4:
                    itemsMenu();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
    private void categoryMenu() {
        while (true) {
            System.out.println("---CATEGORY MENU---");
            System.out.println("1. Add category");
            System.out.println("2. Add sub-category");
            System.out.println("3. Add sub-sub-category");
            System.out.println("4. Apply category discount");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    addSubCategory();
                    break;
                case 3:
                    addSubSubCategory();
                    break;
                case 4:
                    applyCategoryDiscount();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void reportsMenu() {
        while (true) {
            System.out.println("---REPORT MENU---");
            System.out.println("1. View inventory report");
            System.out.println("2. View defective report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    inventoryReport();
                    break;
                case 2:
                    defectiveReport();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void productsMenu() {
        while (true) {
            System.out.println("---PRODUCTS MENU---");
            System.out.println("1. Add product");
            System.out.println("2. View product");
            System.out.println("3. Update product");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void itemsMenu() {
        while (true) {
            System.out.println("---ITEMS MENU---");
            System.out.println("1. Add items of a specific product");
            System.out.println("2. View items of a specific product");
            System.out.println("3. Report a defective item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addItems();
                    break;
                case 2:
                    viewItems();
                    break;
                case 3:
                    reportDefective();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }


    // user choices
    private void addProduct() {
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

        System.out.print("Enter store amount: ");
        int storeAmount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter storage amount: ");
        int storageAmount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter selling price: ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter delivery days: ");
        int deliveryDays = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter minimum amount: ");
        int minimumAmount = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter 1 to add items of this product or 0 otherwise");
        int ans = scanner.nextInt();
        if (ans == 1) addItems();
        scanner.nextLine();

        // TODO
        // Product newProduct = new Product(name, MKT, aisle, producerName, storeAmount, storageAmount, 0, sellingPrice, deliveryDays, minimumAmount);
        // inventorySystem.addProduct(newProduct);

        System.out.println("Product added successfully!");
    }

    private void viewProduct() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();
        // TODO
    }

    private void updateProduct() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();

        System.out.println("1. Update store amount");
        System.out.println("2. Update storage amount");
        System.out.println("3. Update selling price");
        System.out.println("4. Update delivery days");
        System.out.println("5. Update minimum amount");
        System.out.println("6. Update aisle");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter new store amount: ");
                int storeAmount = scanner.nextInt();
                scanner.nextLine();
                //product.setStoreAmount(storeAmount);
                // TODO
                break;
            case 2:
                System.out.print("Enter new storage amount: ");
                int storageAmount = scanner.nextInt();
                scanner.nextLine();
                //product.setStorageAmount(storageAmount);
                break;
            case 3:
                System.out.print("Enter new selling price: ");
                double sellingPrice = scanner.nextDouble();
                scanner.nextLine();
                //product.setSellingPrice(sellingPrice);
                break;
            case 4:
                System.out.print("Enter new delivery days: ");
                int deliveryDays = scanner.nextInt();
                scanner.nextLine();
                //product.setDeliveryDays(deliveryDays);
                break;
            case 5:
                System.out.print("Enter new minimum amount: ");
                int minAmount = scanner.nextInt();
                scanner.nextLine();
                //product.setDeliveryDays(deliveryDays);
                break;
            case 6:
                System.out.print("Enter new aisle: ");
                int aisle = scanner.nextInt();
                scanner.nextLine();
                //product.setSellingPrice(sellingPrice);
                break;
            case 7:
                return;
            default:
                System.out.println("Invalid choice, please try again.");
        }
    }

    private void addItems() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();

//        Product product = inventorySystem.getProductByMKT(MKT);
//        if (product == null) {
//            System.out.println("Product not found.");
//            return;
//        }
//
//        System.out.print("Enter number of items to add: ");
//        int numItems = scanner.nextInt();
//        scanner.nextLine();
//
//        List<Item> items = new ArrayList<>();
//        for (int i = 0; i < numItems; i++) {
//            System.out.print("Enter item ID: ");
//            int itemId = scanner.nextInt();
//            scanner.nextLine();
//
//            System.out.print("Enter expiration date (yyyy-mm-dd): ");
//            String dateStr = scanner.nextLine();
//            Date expirationDate = Date.valueOf(dateStr);
//
//            System.out.print("Enter buying price: ");
//            double buyingPrice = scanner.nextDouble();
//            scanner.nextLine();
//
//            System.out.print("Enter buying discount: ");
//            double buyingDiscount = scanner.nextDouble();
//            scanner.nextLine();
//
//            Item item = new Item(itemId, expirationDate, buyingPrice, buyingDiscount);
//            items.add(item);
        // TODO
    }

    private void viewItems() {
        System.out.print("Enter product MKT: ");
        int MKT = scanner.nextInt();
        scanner.nextLine();

//        Product product = inventorySystem.getProductByMKT(MKT);
//        if (product == null) {
//            System.out.println("Product not found.");
//            return;
//        }
//
//        List<Item> items = product.getItems();
//        for (Item item : items) {
//            System.out.println(item);
//        }
        // TODO
    }

    private void reportDefective() {
        // TODO
    }


    private void applyCategoryDiscount() {
        // TODO
    }

    private void addSubSubCategory() {
        // TODO
    }

    private void addSubCategory() {
        // TODO
    }

    private void addCategory() {
        // TODO
    }

    private void defectiveReport() {
        // TODO
    }

    private void inventoryReport() {
        // TODO
    }

}
