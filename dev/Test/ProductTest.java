package Test;

import domain.Item;
import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        p1 = new Product("milk 3% 500ml", 123, 1, "Tnuva", 6.5, 3, 20);
        p2 = new Product("milk 3% 500ml", 456, 1, "Tara", 7.5, 3, 20);

        //p1.addItemToStorage("2025-01-01", 10, 10);
        // milk 500ml (123) items
        //Item item1 = new Item()
//        addItems(123, 5, "2025-01-01", 10, 10);
//        addItems(123, 5, "2025-01-10", 10, 10);
//        addItems(123, 10, "2025-01-20", 10, 5);
//
//        // Premium Dark Chocolate (1001) items
//        addItems(1001, 5, "2025-01-01", 10, 10);
//        addItems(1001, 5, "2025-01-10", 10, 10);
//        addItems(1001, 10, "2025-01-20", 10, 5);
    }

    @Test
    void testIsUnderMinAmount() {
        assertFalse(p1.isUnderMinAmount());
        assertTrue(p2.isUnderMinAmount());
    }
    @Test
    void testSetAmount() { //TODO not a good test
        p1.setStoreAmount(60);
        assertEquals(p1.getTotalAmount(), 110);
    }
}
