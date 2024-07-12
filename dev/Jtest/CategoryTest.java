import DataLayer.DBConnection;
import DomainLayer.IM.Category;
import DomainLayer.IM.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void testAddCategory() {
        Category category = new Category("Electronics");
        List<Category> categories = categoryRepo.findAll();
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Electronics")));

        categoryRepo.delete(category);
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category("Electronics");
        categoryRepo.delete(category);
        List<Category> categories = categoryRepo.findAll();
        assertFalse(categories.stream().anyMatch(c -> c.getName().equals("Electronics")));
    }

    @Test
    public void testFindAll() {
        List<Category> categories = categoryRepo.findAll();
        assertEquals(18, categories.size());
    }

    @Test
    public void testAddSubCategory() {
        Category parentCategory = new Category("Home");
        Category subCategory = new Category("Furniture", parentCategory);

        List<Category> categories = categoryRepo.findAll();
        assertTrue(categories.stream()
                .anyMatch(c -> c.getName().equals("Furniture") &&
                        c.getParentName().equals("Home")));

        categoryRepo.delete(subCategory);
        categoryRepo.delete(parentCategory);
    }

    @Test
    public void testAddSubSubCategory() {
        Category grandParentCategory = new Category("Electronics");
        Category parentCategory = new Category("Computers", grandParentCategory);
        Category subCategory = new Category("Laptops", parentCategory);

        List<Category> categories = categoryRepo.findAll();

        assertTrue(categories.stream()
                .anyMatch(c -> c.getName().equals("Laptops") &&
                        c.getParentName().equals("Computers")));
        assertTrue(categories.stream()
                .anyMatch(c -> c.getName().equals("Computers") &&
                        c.getParentName().equals("Electronics")));

        categoryRepo.delete(subCategory);
        categoryRepo.delete(parentCategory);
        categoryRepo.delete(grandParentCategory);
    }

    @Test
    public void testDeleteNonExistentCategory() {
        Category category = new Category("NonExistent");

        assertDoesNotThrow(() -> categoryRepo.delete(category));

        List<Category> categories = categoryRepo.findAll();
        assertFalse(categories.stream().anyMatch(c -> c.getName().equals("NonExistent")));
    }
}


