package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private int MKT;
    private int aisle;
    private String producerName;
    private int totalAmount;
    private int storeAmount;
    private int storageAmount;
    private double buyingPrice;
    private double buyingDiscount;
    private  double sellingPrice;
    private int deliveryDays;
    private int minimumAmount;


    private List<Item> items;

    // new product without items
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount,
                   double buyingPrice, double buyingDiscount, double sellingPrice, int deliveryDays, int minimumAmount) {
        this.name = name;
        this.MKT = MKT;
        this.aisle = aisle;
        this.producerName = producerName;
        this.storeAmount = storeAmount;
        this.storageAmount = storageAmount;
        this.buyingPrice = buyingPrice;
        this.buyingDiscount = buyingDiscount;
        this.sellingPrice = sellingPrice;
        this.deliveryDays = deliveryDays;
        this.minimumAmount = minimumAmount;

        this.items = new ArrayList<>();
        this.totalAmount = storeAmount + storageAmount;
    }

    // new product + it's items list
    public Product(String name, int MKT, int aisle, String producerName, int storeAmount, int storageAmount,
                   double buyingPrice, double buyingDiscount, double sellingPrice, int deliveryDays, int minimumAmount, List<Item> items) {
        this(name, MKT, aisle, producerName, storeAmount, storageAmount,
                buyingPrice, buyingDiscount, sellingPrice, deliveryDays, minimumAmount);
        this.items = items;

    }

    public void addItemToStore(Item item) {
        items.add(item);
        storeAmount++;
        totalAmount++;
    }

    public void addItemToStorage(Item item) {
        items.add(item);
        storageAmount++;
        totalAmount++;
    }

    public void removeItemFromStore(Item item) {
        items.remove(item);
        storeAmount--;
        totalAmount--;
    }

    public void addItemFromStorage(Item item) {
        items.remove(item);
        storageAmount--;
        totalAmount--;
    }




    // Getters and setters
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

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(int storeAmount) {
        this.storeAmount = storeAmount;
        this.totalAmount = this.storageAmount + this.storeAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(int storageAmount) {
        this.storageAmount = storageAmount;
        this.totalAmount = this.storageAmount + this.storeAmount;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getBuyingDiscount() {
        return buyingDiscount;
    }

    public void setBuyingDiscount(double buyingDiscount) {
        this.buyingDiscount = buyingDiscount;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
