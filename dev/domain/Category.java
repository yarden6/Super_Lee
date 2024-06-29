package domain;

import domain.Repositories.CategoryRepository;
import domain.Repositories.ProductRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Category {
    private String name;
    private Category parentCategory;
    private Hashtable<String, Category> subCategories; // Holds sub-categories and sub-sub-categories
    private Hashtable<Integer, Product> products; // Only for sub-sub-categories (Integer holds the MKT of the product)
    private int discountPercentage;
    private LocalDate discountDate;

    //just for loading the db TODO
    private String parentName = "";
    private CategoryRepository repository = new CategoryRepository();
    private ProductRepository productRepository = new ProductRepository();


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

        repository.add(this);
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

        repository.add(this);
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

        repository.add(this);
    }

    /**
     * a constructor for the db while loading up the system
     *
     * @param name
     */
    public Category(String name, String parentName, int discountPercentage, LocalDate discountDate) {
        this.name = name;
        this.parentCategory = null;
        this.parentName = parentName;
        this.subCategories = new Hashtable<>();
        this.products = new Hashtable<>();
        this.discountPercentage = discountPercentage;
        this.discountDate = discountDate;
    }

    public void loadData() {
        List<Product> allProducts = productRepository.findAll();
        this.products = allProducts.stream()
                .filter(product -> product.getCategorySubSub().equals(this.name) && product.getCategorySub().equals(this.parentName))
                .collect(Collectors.toMap(Product::getMKT, p -> p, (p1, p2) -> p1, Hashtable::new));
        for(Product product: products.values()){
            product.loadData();

        }
    }

    public void addSubCategory(Category subCategory) {
        subCategories.put(subCategory.getName(), subCategory);
        subCategory.setParentCategory(this);

        repository.add(this);
    }

    // only for sub-sub-category
    public void addProduct(String name, int MKT, int aisle, String producerName, double sellingPrice, int deliveryDays, int minimumAmount, String supplierName) {
        products.put(MKT, new Product(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount, supplierName));
    }

    public boolean isMainCategory() {
        return parentCategory == null;
    }

    public boolean isLeafCategory() {
        return subCategories.isEmpty();
    }

    public String getParentName() {
        return parentName;
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

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        repository.update(this);
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
        repository.update(this);
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
        return "No defective items from this product";
    }
}
