package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private int MKT;
    private int aisle;
    private String supplierName;
    private int totalAmount;
    private int storeAmount;
    private int storageAmount;
    private double buyingPrice;
    private double buyingDiscount; //TODO what was yellow
    private  double sellingPrice;
    private int deliveryDays; //TODO change in the uml to day
    private int minimumAmount;


    private List<Item> items;

    public Product(){ //TODO complete
        items = new ArrayList<>();
    }

}
