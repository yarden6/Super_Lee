package domain;

import java.time.LocalDate;
import java.util.Hashtable;

public class Category {
    private String name;
    private Category parentCategory;
    private Hashtable<String, Category> subCategories; // Holds sub-categories and sub-sub-categories
    private Hashtable<Integer, Product> products; // Only for sub-sub-categories (Integer holds the MKT of the product)
    private int discountPercentage;
    private LocalDate discountDate;


    /**
     * new main category
     *
     * @param name
     */
    public Category(String name) {
        this.name = name;
        this.parentCategory = null;
        this.subCategories = new Hashtable<>();
        this.products = new Hashtable<>();
        this.discountPercentage = 0;
        this.discountDate = null;
    }

    /**
     * new sub-category
     *
     * @param name
     * @param parentCategory
     */
    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategories = new Hashtable<>();
        this.products = new Hashtable<>();
        this.discountPercentage = 0;
        this.discountDate = null;

        parentCategory.addSubCategory(this);
    }

    /**
     * new sub-sub-category
     *
     * @param name
     * @param parentCategory
     * @param products
     */
    public Category(String name, Category parentCategory, Hashtable<Integer, Product> products) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategories = new Hashtable<>();
        this.products = products;
        this.discountPercentage = 0;
        this.discountDate = null;

        parentCategory.addSubCategory(this);
    }


    public void addSubCategory(Category subCategory) {
        subCategories.put(subCategory.getName(), subCategory);
        subCategory.setParentCategory(this);
    }

    // only for sub-sub-category
    public void addProduct(String name, int MKT, int aisle, String producerName, double sellingPrice, int deliveryDays, int minimumAmount) {
        if (isLeafCategory() || (this.name == "Defective")) {
            products.put(MKT, new Product(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount));
        } else {
            System.out.println("Only sub-sub-categories can have products");
        }
    }

    // only for sub-sub-category
    public void removeProduct(Product product) {
        if (products.contains(product)) {
            products.remove(product);
        } else {
            System.out.println("product does not exist");
        }
    }

    public void removeSubCategory(Category subCategory) {
        subCategories.remove(subCategory);
    }

    public boolean isMainCategory() {
        return parentCategory == null;
    }

    public boolean isLeafCategory() {
        return subCategories.isEmpty();
    }


    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public Hashtable<String, Category> getSubCategories() {
        return subCategories;
    }

    public Hashtable<Integer, Product> getProducts() {
        return products;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getDiscountDate() {
        return discountDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setDiscountDate(LocalDate discountDate) {
        this.discountDate = discountDate;
    }

    public void applyDiscount(int discount, LocalDate discountDate) {
        setDiscountPercentage(discount);
        setDiscountDate(discountDate);
    }

    public void reportDefectiveItem(int MKT, int id, String name, String producerName) {
        Product p = getProducts().get(MKT);
        if (p == null) {
            products.put(MKT, new Product(name, MKT, producerName));
        } else
            p.addDefectItem(); // increase the amount
    }

    public String toString() {
        return "Category Name: " + name + '\n';
    }

    public String checkDefective(int mkt) {
        Product productToReturn = getProducts().get(mkt);
        if (productToReturn != null)
            return productToReturn.toString();
        return "Not exist";
    }
}
