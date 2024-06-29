package Test;
import DataLayer.DBConnection;
import domain.Product;
import domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CategoryTest {
    private CategoryRepository categoryRepo;

    @BeforeAll
    public static void setUpClass() {
        DBConnection.connect("SuperLeeTest.db");
    }

    @BeforeEach
    public void setUp() {
        categoryRepo = CategoryRepository.getInstance();
        // Optionally clear or reset data before each test
    }

    @Test
    public void testAddCategory() {
        Category category = new Category("Electronics", null, 10, null);
        categoryRepo.add(category);

        List<Category> categories = categoryRepo.findAll();
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Electronics")));
    }


    @Test
    public void testUpdateCategory() {
        Category category = new Category("Electronics", null, 10, null);
        categoryRepo.add(category);

        category.setDiscountPercentage(15);
        categoryRepo.update(category);

        List<Category> categories = categoryRepo.findAll();
        assertTrue(categories.stream().anyMatch(c -> c.getDiscountPercentage() == 15));
    }

//    @AfterEach
//    public void tearDown() {
//        // Clean up the database if necessary
//    }
//
//    @AfterAll
//    public static void tearDownClass() {
//        // Close connections and clean up test database
//    }
}


