package Test;
import domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    Item item1;
    Item item2;

    @BeforeEach
    void setUp() {
        LocalDate expr1 = LocalDate.of(2025,06,20);
        LocalDate expr2 = LocalDate.of(2024,06,04);
//        item1 = new Item(1, expr1, 3, 3);
//        item2 = new Item(1, expr2, 3, 3);
    }

    @Test
    void isExpired() {
        assertFalse(item1.isExpired());
        assertTrue(item2.isExpired());
    }
}