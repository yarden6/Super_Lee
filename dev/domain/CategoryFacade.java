package domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CategoryFacade {
    private Hashtable<String, Category> categories;

    public CategoryFacade() {
        categories = new Hashtable<>();
        addCategory("Defective");
    }

    public Hashtable<String, Category> getCategories() {
        return categories;
    }

    public boolean addCategory(String categoryName) {
        if (categoryName != null) {
            categories.put(categoryName, new Category(categoryName));
        }
        return true;
    }

    public boolean addSubCategory(String parentCategoryName, String subCategoryName) {
        Category parentCategory = categories.get(parentCategoryName);
        if (parentCategory != null) {
            Category sub = new Category(subCategoryName, parentCategory);
            return true;
        }
        return false;
    }

    public boolean addSubSubCategory(String parentCategoryName, String subCategoryName, String subSubCategoryName) {
        Category parentCategory = categories.get(parentCategoryName);
        if (parentCategory != null) {
            Category subCategory = parentCategory.getSubCategories().get(subCategoryName);
            if (subCategory != null) {
                Category subSubCategory = new Category(subSubCategoryName, subCategory);
                return true;
            }
        }
        return false;
    }


    public String applyCategoryDiscount(String[] categories, int discount, String date) {
        if(!checkValidCategories(categories)){
            return "This categories don't exist";
        }
        Category chosenOne;
        if (categories.length == 1)
            chosenOne = getCategories().get(categories[0]);
        else if (categories.length == 2)
            chosenOne = getCategories().get(categories[0]).getSubCategories().get(categories[1]);
        else
            chosenOne = getCategories().get(categories[0]).getSubCategories().get(categories[1]).getSubCategories().get(categories[2]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate discountEndDate = LocalDate.parse(date, formatter);
            chosenOne.applyDiscount(discount, discountEndDate);
        } catch (DateTimeException e) {
            return ("Error parsing date: " + e.getMessage());
        }
        return "Discount was updated";
    }

    private boolean checkValidCategories(String[] categoriesString) {
        if (categories.containsKey(categoriesString[0])) {
            Category main = categories.get(categoriesString[0]);
            if (categoriesString.length > 1) {
                if (main.getSubCategories().containsKey(categoriesString[1])) {
                    Category sub = main.getSubCategories().get(categoriesString[1]);
                    if (categoriesString.length == 3) {
                        return (sub.getSubCategories().containsKey(categoriesString[2]));
                    }
                    else return true;
                }
            }
            else return true;
        }
        return false;
    }

    public boolean addProduct(String[] categoriesName, String name, int MKT, int aisle, String producerName
            , double sellingPrice, int deliveryDays, int minimumAmount, String supplierName) {
        if (getProduct(MKT) == null) {
            Category mainCategory = categories.get(categoriesName[0]); // locate the desired category
            if (mainCategory != null) {
                if (Objects.equals(mainCategory.getName(), "Defective")) return false;
                Category subCategory = mainCategory.getSubCategories().get(categoriesName[1]);
                if (subCategory != null) {
                    Category subSubCategory = subCategory.getSubCategories().get(categoriesName[2]);
                    if (subSubCategory != null) {
                        subSubCategory.addProduct(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount, supplierName);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Product getProduct(int MKT) {
        for (Category mainCategory : categories.values()) {//for the main category
            if (!Objects.equals(mainCategory.getName(), "Defective")) {
                for (Category subCategory : mainCategory.getSubCategories().values()) { //for the sub category
                    for (Category subSubCategory : subCategory.getSubCategories().values()) { //for the sub-sub category
                        Product productToReturn = subSubCategory.getProducts().get(MKT);
                        if (productToReturn != null)
                            return productToReturn;
                    }
                }
            }
        }
        return null;
    }

    public String viewProduct(int MKT) {
        if (getProduct(MKT) != null)
            return getProduct(MKT).toString();
        else return "Product does not exist";
    }

    public void applyProductDiscount(int MKT, int discount, String date) {
        Product product = getProduct(MKT);
        if (product != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate discountEndDate = LocalDate.parse(date, formatter);
                if (discountEndDate.isAfter(LocalDate.now())) {
                    product.applyDiscount(discount, discountEndDate);
                }
                System.out.println("Date had passed");
            } catch (DateTimeException e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        } else System.out.println("Product does not exist");
    }

    public String addItems(int MKT, int numberOfItems, String expirationDate, double buyingPrice, double buyingDiscount) {
        Product p = getProduct(MKT);
        if (p != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate exprDate = LocalDate.parse(expirationDate, formatter);
                if (exprDate.isAfter(LocalDate.now())) {
                    for (int i = 0; i < numberOfItems; i++) {
                        p.addItemToStorage(exprDate, buyingPrice, buyingDiscount);
                    }
                    return "successfully added";
                } else return "Item is expired";
            } catch (DateTimeException e) {
                return ("Error parsing date: " + e.getMessage());
            }
        }
        else return "Product does not exist";
    }

    public String reportDefectiveItem(int MKT, int id) {
        Product productWithDefect = getProduct(MKT);
        if (productWithDefect != null) {
            categories.get("Defective").reportDefectiveItem(MKT, id, productWithDefect.getName(), productWithDefect.getProducerName());// add the count of the item to the defective products
            productWithDefect.removeDefectiveItem(id); // remove specific item from its product
            checkMakeOrder(productWithDefect);
        }
        return ("Product does not exist");
    }

    public void loadData() {
        addCategory("Dairy products");
        addCategory("Baking products");
        addCategory("Shower products");

        addSubCategory("Dairy products", "Milk");
        addSubCategory("Dairy products", "Cheese");
        addSubCategory("Dairy products", "Cream");
        addSubCategory("Baking products", "Dark Chocolate");
        addSubCategory("Baking products", "Milk Chocolate");
        addSubCategory("Shower products", "Shampoo");
        addSubCategory("Shower products", "Conditioner");

        addSubSubCategory("Dairy products", "Milk", "500ml");
        addSubSubCategory("Dairy products", "Milk", "1000ml");
        addSubSubCategory("Dairy products", "Cheese", "100g");

        addSubSubCategory("Baking products", "Dark Chocolate", "100g");
        addSubSubCategory("Baking products", "Milk Chocolate", "100g");

        addSubSubCategory("Shower products", "Shampoo", "750ml");
        addSubSubCategory("Shower products", "Conditioner", "750ml");


        addProduct(new String[]{"Dairy products", "Milk", "500ml"}, "milk 500ml", 123, 1, "Tnuva", 6.5, 3, 20, "supplierA");
        addProduct(new String[]{"Dairy products", "Milk", "500ml"}, "milk 500ml", 456, 1, "Tara", 7.5, 3, 20, "supplierA");

        addProduct(new String[]{"Baking products", "Dark Chocolate", "100g"}, "Premium Dark Chocolate", 1001, 5, "Top Producer", 2.99, 3, 10, "supplierA");

        addProduct(new String[]{"Dairy products", "Cheese", "100g"}, "Cheddar Cheese 100g", 2002, 7, "Cheese Maker", 3.99, 4, 10, "supplierA");

        addProduct(new String[]{"Shower products", "Shampoo", "750ml"}, "Herbal Shampoo 750ml", 3001, 8, "Shampoo Inc.", 5.49, 5, 25, "supplierA");

        addProduct(new String[]{"Shower products", "Conditioner", "750ml"}, "Silky Conditioner 750ml", 3002, 9, "Conditioner Co.", 4.99, 6, 20, "supplierA");

        // milk 500ml (123) items
        addItems(123, 5, "2025-01-01", 10, 10);
        addItems(123, 5, "2025-01-10", 10, 10);
        addItems(123, 10, "2025-01-20", 10, 5);
        //addItems(123, 1, "2024-06-04", 10, 5);

        // Premium Dark Chocolate (1001) items
        addItems(1001, 5, "2025-01-01", 10, 10);
        addItems(1001, 5, "2025-01-10", 10, 10);
        addItems(1001, 10, "2025-01-20", 10, 5);
    }

    public String restockStore(int MKT, int numItems) {
        if (getProduct(MKT) != null)
            return getProduct(MKT).restockStore(numItems);
        else return "Product does not exist";
    }

    public String updateStoreAfterPurchase(int MKT, String[] itemIDs) {
        Product p = getProduct(MKT);
        if (p != null) {
            for (String id : itemIDs) {
                p.removeItemFromStore(Integer.parseInt(id));
            }
            return checkMakeOrder(p);
        }
        return "There is no such a MKT";
    }

    public String checkDefective(int mkt) {
        return categories.get("Defective").checkDefective(mkt);
    }


    // iterate all the Items and check their expiration date
    // when finding an expired item, add it to the expired list (ans) and report it as defective (which verify the total amount vs the min amount, and make an order if needed)
    public HashMap<Integer, List<Item>> checkExpiration() {
        HashMap<Integer, List<Item>> ans = new HashMap<>();
        for (Category mainCategory : categories.values()) {//for the main category
            if (!Objects.equals(mainCategory.getName(), "Defective")) {
                for (Category subCategory : mainCategory.getSubCategories().values()) { //for the sub category
                    for (Category subSubCategory : subCategory.getSubCategories().values()) { //for the sub-sub category
                        for (Product product : subSubCategory.getProducts().values()) {
                            List<Item> expiredItems = product.checkExpiration();
                            if (!expiredItems.isEmpty()) {
                                ans.put(product.getMKT(), expiredItems);
                                for (Item item : expiredItems) {
                                    reportDefectiveItem(product.getMKT(), item.getItemId());
                                }
                            }
                        }
                    }
                }
            }
        }
        return ans;
    }

    public String viewExistingCategories() {
        StringBuilder printCategories = new StringBuilder();
        for (Category category : categories.values()) {
            if (Objects.equals(category.getName(), "Defective")) continue;
            printCategories.append(category.getName() + "\n");
            for (Category subCategory : category.getSubCategories().values()) {
                printCategories.append("   " + subCategory.getName() + "\n");
                for (Category subSubCategory : subCategory.getSubCategories().values()) {
                    printCategories.append("      " + subSubCategory.getName() + "\n");
                }
            }
            printCategories.append("\n");
        }
        return printCategories.toString();
    }

    public void setSupplier(int MKT, String supplierName) {
        if (getProduct(MKT) != null)
            getProduct(MKT).setSupplier(supplierName);
        else System.out.println("Product does not exist");
    }

    public String checkMakeOrder(Product product) {
        StringBuilder s = new StringBuilder();
        if (product.getMinimum()){ // if the total amount is under the min amount
            s.append("\n-----INVENTORY ALERT!-----\n" + product.toString() + "\n   is almost out of stock!\n");
            if (!product.getWaitingForSupply()){ // if there isn't an order on the way
                s.append("\n-----ORDER INFORMATION-----\n");
                s.append("\nProduct name: " + product.getName());
                s.append("\nProduct MKT: " + product.getMKT());
                s.append("\nProduct supplier: " + product.getSupplier());
                s.append("\nAmount: " + String.valueOf(product.getMinimumAmount()+20));
                product.setWaitingForSupply(true);
            }
            else{
                s.append("\nThe order is on it's way...\n" + product.getMinimumAmount()+20);
            }
             return s.toString();
        }
        else return ("Succeed");
    }
}
