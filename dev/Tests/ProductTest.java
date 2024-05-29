package Tests;

import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        p1 = new Product("milk 3% 500ml", 123, 1, "Tnuva", 50, 50, 6.5, 3, 20);
        p2 = new Product("milk 3% 500ml", 456, 1, "Tara", 5, 5, 7.5, 3, 20);
    }

    @Test
    void testIsUnderMinAmount() {
        assertFalse(p1.isUnderMinAmount());
        assertTrue(p2.isUnderMinAmount());
    }
    @Test
    void testSetAmount() {
        p1.setStoreAmount(60);
        assertEquals(p1.getTotalAmount(), 110);
    }
}
