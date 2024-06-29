package domain;

import domain.Repositories.ItemRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

enum Location {
    Store,
    Storage,
    Defective
}
public class Item {
    private int itemId;
    private LocalDate expirationDate;
    private double buyingPrice;
    private double buyingDiscount;
    private Location location;

    private ItemRepository repository = ItemRepository.getInstance();
    private int productMKT = -1;


    public Item(int itemId , LocalDate expirationDate, double buyingPrice, double buyingDiscount){
        this.itemId = itemId;
        this.expirationDate = expirationDate;
        this.buyingPrice = buyingPrice;
        this.buyingDiscount = buyingDiscount;
        location = Location.Storage; // assuming new items are brought straight to the storage
        repository.add(this);
    }

    /**
     * this constructor is for load data only
     * @param itemId
     * @param productMKT
     * @param expirationDate
     * @param buyingPrice
     * @param buyingDiscount
     * @param location
     */
    public Item(int itemId, int productMKT,LocalDate expirationDate,double buyingPrice,int buyingDiscount,String location){
        this.itemId = itemId;
        this.expirationDate = expirationDate;
        this.buyingDiscount = buyingDiscount;
        this.buyingPrice = buyingPrice;
        if (location == "Store")
            this.location = Location.Store;
        else if (location == "Defective")
            this.location = Location.Defective;
        else
            this.location = Location.Storage;
        this.productMKT = productMKT;
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

    public int getProductMKT() {
        return productMKT;
    }


    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getBuyingDiscount() {
        return buyingDiscount;
    }


    public void setLocationToStore() {
        this.location = Location.Store;
        repository.update(this);
    }

    public void setLocationToDefective() {
        this.location = Location.Defective;
        repository.update(this);
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationName() {
        if (location.toString() == "Store")
            return "Store";
        else if (location.toString() == "Defective")
            return "Defective";
        else
            return "Storage";
    }
}
