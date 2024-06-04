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
        if(categoryName != null) {
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


    public void applyCategoryDiscount(String[] categories, int discount, String date) {
        Category chosenOne;
        if (categories.length == 1)
            chosenOne = getCategories().get(categories[0]);
        if (categories.length == 2)
            chosenOne = getCategories().get(categories[0]).getSubCategories().get(categories[1]);
        else
            chosenOne = getCategories().get(categories[0]).getSubCategories().get(categories[1]).getSubCategories().get(categories[2]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate discountEndDate = LocalDate.parse(date, formatter);
            chosenOne.applyDiscount(discount, discountEndDate);
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void addProduct(String[] categoriesName, String name, int MKT, int aisle, String producerName
            , double sellingPrice, int deliveryDays, int minimumAmount) {

        Category mainCategory = categories.get(categoriesName[0]); // locate the desired category
        if (mainCategory != null) {
            Category subCategory = mainCategory.getSubCategories().get(categoriesName[1]);
            if (subCategory != null) {
                Category subSubCategory = subCategory.getSubCategories().get(categoriesName[2]);
                if (subSubCategory != null) {
                    subSubCategory.addProduct(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount);
                    return;
                }
            }
        }
        System.out.println("This category doesn't exist");
    }

    public Product getProduct(int MKT) {
        for (Category mainCategory : categories.values()) {//for the main category
            if (mainCategory.getName() != "Defective") {
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
        return getProduct(MKT).toString();
    }

    public void applyProductDiscount(int MKT, int discount, String date) {
        Product product = getProduct(MKT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate discountEndDate = LocalDate.parse(date, formatter);
            product.applyDiscount(discount, discountEndDate);
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void addItems(int MKT, int numberOfItems, String expirationDate, double buyingPrice, double buyingDiscount) {
        Product p = getProduct(MKT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate exprDate = LocalDate.parse(expirationDate, formatter);
            for (int i = 0; i < numberOfItems; i++) {
                p.addItemToStorage(exprDate, buyingPrice, buyingDiscount);
            }
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

    }

    public void reportDefectiveItem(int MKT, int id) {
        Product productWithDefect = getProduct(MKT);
        categories.get("Defective").reportDefectiveItem(MKT, id, productWithDefect.getName(), productWithDefect.getProducerName());// add the count of the item to the defective products
        productWithDefect.removeDefectiveItem(id); // remove specific item from its product
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


        addProduct(new String[]{"Dairy products", "Milk", "500ml"}, "milk 500ml", 123, 1, "Tnuva", 6.5, 3, 20);
        addProduct(new String[]{"Dairy products", "Milk", "500ml"}, "milk 500ml", 456, 1, "Tara", 7.5, 3, 20);

        addProduct(new String[]{"Baking products", "Dark Chocolate", "100g"}, "Premium Dark Chocolate", 1001, 5, "Top Producer", 2.99, 3, 10);

        addProduct(new String[]{"Dairy products", "Cheese", "100g"}, "Cheddar Cheese 100g", 2002, 7, "Cheese Maker", 3.99, 4, 10);

        addProduct(new String[]{"Shower products", "Shampoo", "750ml"}, "Herbal Shampoo 750ml", 3001, 8, "Shampoo Inc.", 5.49, 5, 25);

        addProduct(new String[]{"Shower products", "Conditioner", "750ml"}, "Silky Conditioner 750ml", 3002, 9, "Conditioner Co.", 4.99, 6, 20);

        // milk 500ml (123) items
        addItems(123, 5, "2025-01-01", 10, 10);
        addItems(123, 5, "2025-01-10", 10, 10);
        addItems(123, 10, "2025-01-20", 10, 5);

        // Premium Dark Chocolate (1001) items
        addItems(1001, 5, "2025-01-01", 10, 10);
        addItems(1001, 5, "2025-01-10", 10, 10);
        addItems(1001, 10, "2025-01-20", 10, 5);
    }

    public String restockStore(int MKT, int numItems) {
        return getProduct(MKT).restockStore(numItems);
    }

    public boolean updateStoreAfterPurchase(int MKT, String[] itemIDs) {
        Product p = getProduct(MKT);
        if(p != null) {
            for (String id : itemIDs)
                if(p.removeItemFromStore(Integer.parseInt(id)))
                    return true;
        }
        return false;
    }

    public String checkDefective(int mkt) {
        return categories.get("Defective").checkDefective(mkt);
    }
}
