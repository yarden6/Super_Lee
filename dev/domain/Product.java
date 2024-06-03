package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private int discount;
    private LocalDate discountDate;
    private int itemsCounter = 1;


    private List<Item> items;

    // new product without items
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount, double sellingPrice, int deliveryDays, int minimumAmount) {
        this.name = name;
        this.MKT = MKT;
        this.aisle = aisle;
        this.producerName = producerName;
        this.storeAmount = storeAmount;
        this.storageAmount = storageAmount;
        this.sellingPrice = sellingPrice;
        this.deliveryDays = deliveryDays;
        this.minimumAmount = minimumAmount;
        this.discount = 0;
        this.discountDate = null;

        this.items = new ArrayList<>();
        this.totalAmount = storeAmount + storageAmount;
    }

    // new product + it's items list
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount, double sellingPrice, int deliveryDays, int minimumAmount, List<Item> items) {
        this(name, MKT, aisle, producerName, sellingPrice, deliveryDays, minimumAmount);
        this.items = items;
    }

    public void addItemToStore(Item item) {
        items.add(item);
        storeAmount++;
        totalAmount++;
    }

    // when we get new items from the supplier - it goes straight to the storage
    public void addItemToStorage(LocalDate expirationDate, double buyingPrice, double buyingDiscount) {
        items.add(new Item(itemsCounter, expirationDate, buyingPrice, buyingDiscount));
        itemsCounter++;
        storageAmount++;
        totalAmount++;
    }

    // when an item is bought from the store
    public void removeItemFromStore(Item item) {
        items.remove(item);
        storeAmount--;
        totalAmount--;
        checkMinAmountAlert(); // alert if needed
    }

    public void addItemFromStorage(Item item) {
        items.remove(item);
        storageAmount--;
        totalAmount--;
    }

    public boolean isUnderMinAmount(){
        return (this.totalAmount <= this.minimumAmount);
    }

    public void checkMinAmountAlert(){
        if (isUnderMinAmount())
            System.out.println("\n-----INVENTORY ALERT!-----\n" + this.toString() + "\nis almost out of stock!");
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

    public int getDiscount() {
        return discount;
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

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Product Name: " + name + '\n' +
                "MKT: " + MKT + '\n' +
                "Producer Name: " + producerName + '\n' +
                "Total Amount: " + totalAmount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setDiscountDate(LocalDate discountDate) {
        this.discountDate = discountDate;
    }

    public void applyDiscount (int discount, LocalDate discountDate){
        setDiscount(discount);
        setDiscountDate(discountDate);
    }

    public List<Item> checkExpiration(){
        List<Item> expired = new ArrayList<>();
        for (Item item: items){
            if(item.isExpired()){
                if (item.getLocation() == Location.Store)
                    setStoreAmount(--storeAmount);
                else setStorageAmount(--storageAmount);
                item.setLocationToDefective();
                expired.add(item);
                items.remove(item);
            }
        }
        return items;
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
