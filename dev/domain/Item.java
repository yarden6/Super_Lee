package domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Item {
    private int itemId;
    private LocalDate expirationDate;
    private double buyingPrice;
    private double buyingDiscount;

    // new item without supplier discount
    public Item(int itemId , LocalDate expirationDate, double buyingPrice){
        this.itemId = itemId;
        this.expirationDate = expirationDate;
        this.buyingPrice = buyingPrice;
        this.buyingDiscount = buyingPrice; // same price since there is no discount
    }

    // new item + original buying price + buying price after supplier discount
    public Item(int itemId , LocalDate expirationDate, double buyingPrice, double buyingDiscount){
        this.itemId = itemId;
        this.expirationDate = expirationDate;
        this.buyingPrice = buyingPrice;
        this.buyingDiscount = buyingDiscount;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getItemId() {
        return itemId;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getBuyingDiscount() {
        return buyingDiscount;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setBuyingDiscount(double buyingDiscount) {
        this.buyingDiscount = buyingDiscount;
    }


}
