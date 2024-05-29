package Tests;
import domain.Product;
import domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CategoryTest {
    private Product p1;
    private Product p2;
    private Category mainCategory1;
    private Category mainCategory2;
    private Category subCategory1;
    private Category subCategory2;
    private Category subCategory3;
    private Category subSubCategory;

    @BeforeEach
    void setUp() {
        p1 = new Product("milk 500ml", 123, 1, "Tnuva", 50, 50, 6.5, 3, 20);
        p2 = new Product("milk 500ml", 456, 1, "Tara", 5, 5, 7.5, 3, 20);
        List<Product> l1 = new ArrayList<Product>();
        l1.add(p1);
        l1.add(p2);

        mainCategory1 = new Category("Dairy products");
        mainCategory2 = new Category("Baking products");

        subCategory1 = new Category("Milk", mainCategory1);
        subCategory2 = new Category("Cheese");
        subCategory3 = new Category("Cream");

        subSubCategory = new Category("500ml", subCategory1, l1);
    }

    @Test
    void testAddSubCategory() {
        mainCategory1.addSubCategory(subCategory2);
        assertEquals(mainCategory1.getSubCategories().size(), 2);
    }
    @Test
    void testIsMainCategory() {
        assertFalse(subCategory1.isMainCategory());
        mainCategory1.addSubCategory(subCategory3);
        assertFalse(subCategory3.isMainCategory());
        assertFalse(subSubCategory.isMainCategory());
        assertTrue(mainCategory2.isMainCategory());
    }

    @Test
    void testIsLeafCategory() {
        assertTrue(subSubCategory.isLeafCategory());
        assertFalse(subCategory1.isLeafCategory());
        assertTrue(mainCategory2.isLeafCategory());
        assertFalse(mainCategory1.isLeafCategory());
    }
}


