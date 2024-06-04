package Test;

import domain.Item;
import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product p1;
    private Product p2;

    String expectedMsg;
    String functionMsg;

    @BeforeEach
    void setUp() {
        p1 = new Product("milk 3% 500ml", 123, 1, "Tnuva", 6.5, 3, 20);
        p2 = new Product("milk 3% 500ml", 456, 1, "Tara", 7.5, 3, 5);

        LocalDate expr = LocalDate.of(2025,06,20);
        for(int i = 0; i<25;i++)
            p1.addItemToStorage(expr,3.20,3.00);
        expr = LocalDate.of(2024,12,3);
        for(int i = 0; i<7;i++)
            p2.addItemToStorage(expr,3.20,3.00);
    }
    @Test
    /**
     * the product is more than the min amount
     */
    void testCheckMinAmountAlert1(){
//        functionMsg = ;
//        assertEquals(p1.checkMinAmountAlert(),expectedMsg);
    }
    /**
     * the product is less than the min amount
     */
    @Test
    void testCheckMinAmountAlert2(){
        assertEquals(functionMsg,expectedMsg);

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
