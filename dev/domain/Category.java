package domain;

import java.util.Date;
import java.util.List;

public class Category {
    private String name;
    private Category subCategory;
    private List <Category> subCategories; //when I am in sub-sub class - will be empty
    private List <Product> products; //when I am in the first div category will be empty
    private int discount;
    private Date discountDate;
}
