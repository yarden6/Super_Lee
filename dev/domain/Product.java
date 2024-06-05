package domain;

import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;

public class Product {
    private String name;
    private int MKT;
    private int aisle;
    private String producerName;
    private int totalAmount;
    private int storeAmount;
    private int storageAmount;
    private  double sellingPrice;
    private int deliveryDays;
    private int minimumAmount;
    private int discountPercentage;
    private LocalDate discountDate;
    private int itemsCounter = 1;
    private List<Item> items;

    // new product without items
    public Product(String name, int MKT, int aisle, String producerName, double sellingPrice, int deliveryDays, int minimumAmount) {
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

        this.items = new ArrayList<>();
        this.totalAmount = storeAmount + storageAmount;
    }

    // new product + it's items list
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount, double sellingPrice, int deliveryDays, int minimumAmount, List<Item> items) {
        this(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount);
        this.items = items;
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

        this.items = new ArrayList<>();
        this.totalAmount = 1;
    }


    // when we get new items from the supplier - it goes straight to the storage
    public Item addItemToStorage(LocalDate expirationDate, double buyingPrice, double buyingDiscount) {
        Item toAdd = new Item(itemsCounter, expirationDate, buyingPrice, buyingDiscount);
        items.add(toAdd);
        itemsCounter++;
        storageAmount++;
        totalAmount++;
        return toAdd;
    }


    public boolean removeItemFromStore(int itemID) {
        for (Item item : items){
            if(item.getItemId() == itemID){
                items.remove(item);
                storeAmount--;
                totalAmount--;
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
        if (isUnderMinAmount())
            System.out.println("\n-----INVENTORY ALERT!-----\n" + this.toString() + "\n   is almost out of stock!\n");
    }

    public void addDefectItem(){
        this.totalAmount++;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMKT() {
        return MKT;
    }

    public void setMKT(int MKT) {
        this.MKT = MKT;
    }

    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
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
        }
        else {
            System.out.println("Invalid Price");
        }
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(int deliveryDays) {
        this.deliveryDays = deliveryDays;
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

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
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

    public void setItems(List<Item> items) {
        this.items = items;
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

    public void setDiscountDate(LocalDate discountDate) {
        this.discountDate = discountDate;
    }

    public void applyDiscount (int discount, LocalDate discountDate){
        setDiscountPercentage(discount);
        setDiscountDate(discountDate);
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
        String itemsId ="items ID: ";
        if (this.storageAmount < numItems){
            return "There are not enough items from this product at the storage";
        }
        for(Item item : getItemsSortedByExpirationDate()){
            if (numItems != 0 && (item.getLocation() == Location.Storage)){
                item.setLocationToStore();
                itemsId.concat(item.getItemId() + ", ");
            }
        }
        return itemsId.substring(0,itemsId.length()-2);
    }
}
