package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Category {
    private String name;
    private Category parentCategory;
    private List<Category> subCategories; // Holds sub-categories and sub-sub-categories
    private List<Product> products; // Only for sub-sub-categories
    private int discount;
    private Date discountDate;

    // new main category
    public Category(String name) {
        this.name = name;
        this.parentCategory = null;
        this.subCategories = new ArrayList<>();
        this.products = new ArrayList<>();
        this.discount = 0;
        this.discountDate = null;
    }

    // new sub-category
    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategories = new ArrayList<>();
        this.products = new ArrayList<>();
        this.discount = 0;
        this.discountDate = null;

        parentCategory.addSubCategory(this);
    }

    // new sub-sub-category
    public Category(String name, Category parentCategory, List<Product> products) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategories = new ArrayList<>();
        this.products = products;
        this.discount = 0;
        this.discountDate = null;

        parentCategory.addSubCategory(this);
    }


    public void addSubCategory(Category subCategory) {
        subCategories.add(subCategory);
        subCategory.setParentCategory(this);
    }

    // only for sub-sub-category
    public void addProduct(Product product) {
        if (isLeafCategory()) {
            products.add(product);
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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getDiscount() {
        return discount;
    }

    public Date getDiscountDate() {
        return discountDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setDiscountDate(Date discountDate) {
        this.discountDate = discountDate;
    }
}
