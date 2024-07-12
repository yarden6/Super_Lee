package DomainLayer.IM;

import DomainLayer.IM.Repositories.CategoryRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CategoryFacade {
    private Hashtable<String, Category> categories;
    private Hashtable<Integer, Product> productsOutOfStock;
    private CategoryRepository categoryRepository = CategoryRepository.getInstance() ;

    public CategoryFacade() {
        categories = new Hashtable<>();
        productsOutOfStock = new Hashtable<>();
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
            if (discountEndDate.isAfter(LocalDate.now())) {
                chosenOne.applyDiscount(discount, discountEndDate);
            }
            else return "Date had passed";
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
                        subSubCategory.addProduct(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount, supplierName,categoriesName[0],categoriesName[1],categoriesName[2]);
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
                else System.out.println("Date had passed");
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
                    if (!p.isUnderMinAmount() & productsOutOfStock.get(p.getMKT()) !=null){ //checking if after restocking i got to more products then the min amount if so i will delete the product
                        productsOutOfStock.remove(p.getMKT());
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
            return checkMakeOrder(productWithDefect);
        }
        return ("Product does not exist");
    }

    public void loadData() {
        List<Category> allCategories = categoryRepository.findAll();
        for(Category category: allCategories){
            String parentCategory = category.getParentName();
            if(parentCategory != null){
                Category parent = allCategories.stream().filter(c -> c.getName().equals(parentCategory)).findAny().get();
                category.setParentCategory(parent); //updating the parent to the category
               //updating the category for the subs of the parent
                parent.getSubCategories().put(category.getName(), category);
            }
            else{
                categories.put(category.getName(),category); //saving the main category
            }
        }
        for(Category category: allCategories) {
            if(category.isLeafCategory() || category.getName() == "Defective") //TODO LEAF
                category.loadData();
            }
    }

    public String restockStore(int MKT, int numItems) {
        if (getProduct(MKT) != null) {
            Product p = getProduct(MKT);
            return p.restockStore(numItems);
        }
        else return "Product does not exist";
    }

    public Hashtable<Integer, Product> getProductsOutOfStock() {
        return productsOutOfStock;
    }

    public String updateStoreAfterPurchase(int MKT, String[] itemIDs) {
        StringBuilder response = new StringBuilder();
        Product p = getProduct(MKT);
        boolean check = true;
        if (p != null) {
            for (String id : itemIDs) {
                if (!p.removeItemFromStore(Integer.parseInt(id))){
                    response.append("itemID "+id +" doesn't exist\n");
                }
            }

            return response.append(checkMakeOrder(p)).toString();
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
                productsOutOfStock.put(product.getMKT(), product); //adding the missing products into a map
            }
            else{
                s.append("\n   The order is on it's way...\n");
            }
             return s.toString();
        }
        else return ("Succeed");
    }
}
