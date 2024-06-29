package domain;

import domain.Repositories.ItemRepository;
import domain.Repositories.ProductRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.MAX_VALUE;

public class Product {
    private String name;
    private int MKT;
    private int aisle;
    private String producerName;
    private int totalAmount;
    private int storeAmount;
    private int storageAmount;
    private double sellingPrice;
    private int deliveryDays;
    private int minimumAmount;
    private int discountPercentage;
    private LocalDate discountDate;
    private int itemsCounter = 1;
    private List<Item> items;
    private String supplier;
    private Boolean waitingForSupply; // in case there is an order on the way
    private Boolean isMinimum; // in case the total amount in under the min amount

    //this is for the load data only
    private String categoryMain = "";
    private String categorySub = "";
    private String categorySubSub = "";
    private ProductRepository repository = ProductRepository.getInstance();
    private ItemRepository itemRepository = ItemRepository.getInstance();

    // new product without items
    public Product(String name, int MKT, int aisle, String producerName, double sellingPrice, int deliveryDays, int minimumAmount, String supplierName) {
        this.name = name;
        this.MKT = MKT;
        this.aisle = aisle;
        this.producerName = producerName;
        this.storeAmount = 0;
        this.storageAmount = 0;
        this.sellingPrice = sellingPrice;
        this.deliveryDays = deliveryDays;
        this.minimumAmount = minimumAmount;
        this.discountPercentage = 0;
        this.discountDate = null;
        this.supplier = supplierName;
        this.isMinimum = isUnderMinAmount();
        this.waitingForSupply = false;
        this.items = new ArrayList<>();
        this.totalAmount = storeAmount + storageAmount;

        repository.add(this);
    }

    /**
     * this is for the db loading
     */
    public Product(int MKT,String categoryMain,String categorySub, String categorySubSub,String name,int aisle,String producerName,int totalAmount,int storeAmount,int storageAmount,double sellingPrice,int deliveryDays,int minimumAmount,int discountPercentage,LocalDate discountDate,String supplier) {
        this.name = name;
        this.MKT = MKT;
        this.aisle = aisle;
        this.producerName = producerName;
        this.storeAmount = storeAmount;
        this.storageAmount = storageAmount;
        this.sellingPrice = sellingPrice;
        this.deliveryDays = deliveryDays;
        this.minimumAmount = minimumAmount;
        this.discountPercentage = discountPercentage;
        this.discountDate = discountDate;
        this.supplier = supplier;
        this.isMinimum = isUnderMinAmount();
        this.waitingForSupply = false;
        this.items = new ArrayList<>();
        this.totalAmount = totalAmount;
        this.categoryMain = categoryMain;
        this.categorySub = categorySub;
        this.categorySubSub = categorySubSub;
    }

    // new product + it's items list
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount, double sellingPrice, int deliveryDays, int minimumAmount, List<Item> items,String supplierName) {
        this(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount,supplierName);
        this.items = items;

        repository.add(this);
    }

    // new defected product
    public Product(String name, int MKT, String producerName){
        this.name = name;
        this.MKT = MKT;
        this.aisle = 0;
        this.producerName = producerName;
        this.storeAmount = 0;
        this.storageAmount = 0;
        this.sellingPrice = 0;
        this.deliveryDays = 0;
        this.minimumAmount = MAX_VALUE; // so that it won't alert
        this.discountPercentage = 0;
        this.discountDate = null;

        this.categoryMain = "Defective"; //TODO

        this.items = new ArrayList<>();
        this.totalAmount = 1;

        repository.add(this);
    }

    public void loadData() {
        List<Item> allItems = itemRepository.findAll();
        this.items = allItems.stream()
                .filter(item -> item.getProductMKT() == this.MKT)
                .collect(Collectors.toList());

    }

    // when we get new items from the supplier - it goes straight to the storage
    public Item addItemToStorage(LocalDate expirationDate, double buyingPrice, double buyingDiscount) {
        Item toAdd = new Item(itemsCounter, expirationDate, buyingPrice, buyingDiscount);
        items.add(toAdd);
        itemsCounter++;
        storageAmount++;
        totalAmount++;
        if (!isUnderMinAmount()){
            this.isMinimum = false; // total amount > min amount
            this.waitingForSupply = false; // the new supply arrived
        }
        repository.update(this);
        return toAdd;
    }


    public boolean removeItemFromStore(int itemID) {
        for (Item item : items){
            if(item.getItemId() == itemID){
                items.remove(item);
                if (item.getLocation() == Location.Store) {
                    setStoreAmount(--this.storeAmount);
                } else {
                    setStorageAmount(--this.storageAmount);
                }
                checkMinAmountAlert(); // alert if needed
                return true;
            }
        }
        return false;
    }

    public boolean isUnderMinAmount(){
        return (this.totalAmount <= this.minimumAmount);
    }

    public void checkMinAmountAlert(){
        if (isUnderMinAmount()) {
            isMinimum = true;
        }
        else isMinimum = false;
    }

    // only for a defective product. regular products dont get here
    public void addDefectItem(){
        this.totalAmount++;
        repository.update(this);
    }
    public String getName() {
        return name;
    }

    public int getMKT() {
        return MKT;
    }

    public Boolean getMinimum() {
        return isMinimum;
    }

    public int getAisle() {
        return aisle;
    }

    public Boolean getWaitingForSupply() {
        return waitingForSupply;
    }

    public void setWaitingForSupply(Boolean waitingForSupply) {
        this.waitingForSupply = waitingForSupply;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
        repository.update(this);
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(int storeAmount) {
        if (storeAmount >= 0){
            this.storeAmount = storeAmount;
            this.totalAmount = this.storageAmount + this.storeAmount;
            checkMinAmountAlert();
            repository.update(this);
        }
        else{
            System.out.println("invalid new amount");
        }
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        if (storageAmount >= 0){
            this.storageAmount = storageAmount;
            this.totalAmount = this.storageAmount + this.storeAmount;
            checkMinAmountAlert();
            repository.update(this);
        }
        else{
            System.out.println("invalid new amount");
        }
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        if (sellingPrice > 0){
            this.sellingPrice = sellingPrice;
            repository.update(this);
        }
        else {
            System.out.println("Invalid Price");
        }
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getDiscountDate() {
        return discountDate;
    }
    public String getDiscountDateString() {
        if (discountDate != null) return discountDate.toString();
        else return null;
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeDefectiveItem(int itemID){
        for (Item item : items){
            if (item.getItemId() == itemID) {
                items.remove(item);
                if (item.getLocation() == Location.Store) {
                    setStoreAmount(--this.storeAmount);
                } else {
                    setStorageAmount(--this.storageAmount);
                }
                item.setLocationToDefective();
                return;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("      Product Name: ").append(name).append('\n')
                .append("      MKT: ").append(MKT).append('\n')
                .append("      Producer Name: ").append(producerName).append('\n')
                .append("      Total Amount: ").append(totalAmount).append('\n')
                .append("      Selling Price: ").append(sellingPrice).append('\n');
        if (this.discountPercentage != 0) {
            response.append("      Discount Percentage: ").append(discountPercentage).append('\n')
                    .append("      Discount Date: ").append(discountDate.toString()).append('\n')
                    .append("      Final Selling Price: ").append(sellingPrice * ((100 - discountPercentage) / 100.0)).append('\n');
        }
        return response.toString();
    }


    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
        repository.update(this);
    }

    public String getSupplier() {
        return supplier;
    }

    public void setDiscountDate(LocalDate discountDate) {
        this.discountDate = discountDate;
    }

    public void applyDiscount (int discount, LocalDate discountDate){
        setDiscountPercentage(discount);
        setDiscountDate(discountDate);
        repository.update(this);
    }

    public String getCategoryMain() {
        return categoryMain;
    }

    public String getCategorySub() {
        return categorySub;
    }

    public String getCategorySubSub() {
        return categorySubSub;
    }

    // forward a list of expires items that need to be removed and report
    public List<Item> checkExpiration(){
        List<Item> expired = new ArrayList<>();
        for (Item item: items){
            if(item.isExpired()){
                expired.add(item);
            }
        }
        return expired;
    }

    public List<Item> getItemsSortedByExpirationDate() {
        List<Item> sortedItems = new ArrayList<>(items);
        Collections.sort(sortedItems, Comparator.comparing(Item::getExpirationDate));
        return sortedItems;
    }
    public String restockStore(int numItems) { // move items from storage to store
        if (this.storageAmount < numItems){
            return ("There are not enough items from this product at the storage\n(Only " + this.storageAmount + " items at the storage)");
        }
        StringBuilder itemsId =new StringBuilder("The items you should transformed by the closest expiration date are:\n  items ID:  ");
        for(Item item : getItemsSortedByExpirationDate()){
            if (numItems != 0 && (item.getLocation() == Location.Storage)){
                item.setLocationToStore();
                this.storeAmount++;
                this.storageAmount--;
                itemsId.append(item.getItemId() + ", ");
                numItems--;
            }
        }
        repository.update(this);
        return itemsId.substring(0,itemsId.length()-2);
    }
}
