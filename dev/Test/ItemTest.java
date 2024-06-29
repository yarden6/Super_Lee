package Test;
import domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void testNoExpired() {
        List<Item> items = itemRepository.findAll();
        assertFalse(items.stream().anyMatch(i -> i.isExpired()));
    }

    @Test
    public void testAddItem() {
        LocalDate expirationDate = LocalDate.of(2025, 1, 1);
        Item item = new Item(100, expirationDate, 10.5, 10.5, 2002);

        List<Item> items = itemRepository.findAll();
        assertTrue(items.stream().anyMatch(i -> i.getItemId() == 100));

        itemRepository.delete(item);
    }

    @Test
    public void testDeleteItem() {
        LocalDate expirationDate = LocalDate.of(2025, 1, 1);
        Item item = new Item(100, expirationDate, 10.5, 10.5, 2002);

        itemRepository.delete(item);
        List<Item> items = itemRepository.findAll();
        assertFalse(items.stream().anyMatch(i -> i.getItemId() == 100));
    }

    @Test
    public void testFindAll() {
        List<Item> items = itemRepository.findAll();
        assertEquals(31, items.size());
    }

    @Test
    public void testDeleteNonExistentItem() {
        LocalDate expirationDate = LocalDate.of(2025, 1, 1);
        Item item = new Item(100, expirationDate, 10.5, 10.5, 2002);

        assertDoesNotThrow(() ->  itemRepository.delete(item));

        List<Item> items = itemRepository.findAll();
        assertFalse(items.stream().anyMatch(i -> i.getItemId() == 100));
    }
}