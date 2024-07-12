import DataLayer.DBConnection;
import DomainLayer.IM.*;
import DomainLayer.IM.Repositories.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private ProductRepository productRepo;

    @BeforeAll
    public static void setUpClass() {
        DBConnection.connect("SuperLeeTest.db");
    }

    @BeforeEach
    public void setUp() {
        productRepo = ProductRepository.getInstance();
        CategoryFacade cf = new CategoryFacade();
        cf.loadData();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Mint Shampoo 750ml", 111, 5, "Head&Shoulder", 11.5, 30, 20, "aaa", "Shower products","Shampoo", "750ml");
        List<Product> products = productRepo.findAll();
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Mint Shampoo 750ml")));

        productRepo.delete(product);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product("Mint Shampoo 750ml", 111, 5, "Head&Shoulder", 11.5, 30, 20, "aaa", "Shower products","Shampoo", "750ml");

        productRepo.delete(product);
        List<Product> products = productRepo.findAll();
        assertFalse(products.stream().anyMatch(p -> p.getMKT() == 222));
    }

    @Test
    public void testFindAll() {
        List<Product> products = productRepo.findAll();
        assertEquals(7, products.size());
    }

    @Test
    public void testDeleteNonExistentProduct() {
        Product product = new Product("NonExistent", 111, 5, "Head&Shoulder", 11.5, 30, 20, "aaa", "Shower products","Shampoo", "750ml");

        assertDoesNotThrow(() -> productRepo.delete(product));

        List<Product> products = productRepo.findAll();
        assertFalse(products.stream().anyMatch(p -> p.getName().equals("NonExistent")));
    }

//    @Test
//    public void testUpdateProduct() {
//        Product product = new Product("Laptop", 222, "aaa");
//
//        product.set
//        // Update the product's price
//        product.setSupplier(1099.99);
//        productRepo.update(product);
//
//        // Retrieve the updated product from the repository
//        Product updatedProduct = productRepo.findById(product.getId());
//
//        // Assert that the product's price has been updated
//        assertEquals(1099.99, updatedProduct.getPrice());
//
//        // Clean up: delete the product after the test
//        productRepo.delete(updatedProduct);
//    }
}
