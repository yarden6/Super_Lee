import DataLayer.DBConnectionIM;
import DomainLayer.IM.Item;
import DomainLayer.IM.Repositories.ItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private ItemRepository itemRepository;

    @BeforeAll
    public static void setUpClass() {
        DBConnectionIM.connect("SuperLeeTest.db");
    }

    @BeforeEach
    public void setUp() {
        itemRepository = ItemRepository.getInstance();
    }

    @Test
    void testIsExpired() {
        LocalDate expirationDate = LocalDate.of(2025, 1, 1);
        Item item = new Item(100, expirationDate, 10.5, 10.5, 2002);

        assertFalse(item.isExpired());

        itemRepository.delete(item);
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