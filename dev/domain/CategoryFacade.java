package domain;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

public class CategoryFacade {
    private Hashtable<String, Category> categories;

    public CategoryFacade() {
        categories = new Hashtable<>();
    }

    public Hashtable<String, Category> getCategories() {
        return categories;
    }

    public void addCategory(String categoryName) {
        categories.put(categoryName, new Category(categoryName));

    }

    public void addSubCategory(String categoryName, String subName) {
        Category parentCategory = categories.get(categoryName);
        if (parentCategory != null) {
            Category sub = new Category(subName, parentCategory);
        }

    }

    public void addSubSubCategory(String categoryName, String subName, String subSubName) {
        Category parentCategory = categories.get(categoryName);
        if (parentCategory != null) {
            Category sub = parentCategory;//TODO
            Category subSub = new Category(subName, parentCategory);
        }
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
            , int storeAmount, int storageAmount, double sellingPrice, int deliveryDays, int minimumAmount) {

        Category category = categories.get(categoriesName[0]).getSubCategories().get(categoriesName[1]).getSubCategories().get(categoriesName[2]); // locate the desired category
        category.addProduct(name, MKT, aisle, producerName
                , storeAmount, storageAmount, sellingPrice, deliveryDays, minimumAmount);
    }

    public Product getProduct(int MKT) {
        for (Category mainCategory : categories.values()) {//for the main category
            for (Category subCategory : mainCategory.getSubCategories().values()) { //for the sub category
                for (Category subSubCategory : subCategory.getSubCategories().values()) { //for the sub-sub category
                    return subSubCategory.getProducts().get(MKT);
                }
            }
        }
        return null;
    }

    public String viewProduct (int MKT){
        return getProduct(MKT).toString();
    }

    public void applyProductDiscount(int MKT, int discount, String date) {
        Product product = getProduct(MKT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate discountEndDate = LocalDate.parse(date, formatter);
            product.applyDiscount(discount,discountEndDate);
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    public void setProductStoreAmount (int MKT, int amount){
        getProduct(MKT).setStoreAmount(amount);
    }

    public void setProductStorageAmount (int MKT, int amount){
        getProduct(MKT).setStorageAmount(amount);
    }

    public void addItems (int MKT, int numberOfItems, String expirationDate, double buyingPrice, double buyingDiscount){
        Product p = getProduct(MKT);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate exprDate = LocalDate.parse(expirationDate, formatter);
            for (int i=0; i<numberOfItems; i++){
                p.addItemToStorage(exprDate, buyingPrice, buyingDiscount);
            }
        } catch (DateTimeException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }

    }
}
