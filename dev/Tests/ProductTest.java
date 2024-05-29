package Tests;

import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {
    private Product product;
    @BeforeEach
    void setUp() {
        // Initialize the product with some sample data
        product = new Product("Example Product", 12345, 1, "Producer", 100, 50, 50, 10.0, 0.1, 15, 5);
    }

    @Test
    void testGetName() {
        //assertEquals("Example Product", product.getName());
    }

    @Test
    void testGetTotalAmount() {
        //assertEquals(100, product.getTotalAmount());
    }
}
