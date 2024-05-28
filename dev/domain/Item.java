package domain;

import java.util.Date;

public class Item {
    private int itemId;
    private Date expirationDate;
    public Item( int itemId , Date expirationDate){
        this.itemId = itemId;
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getItemId() {
        return itemId;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
