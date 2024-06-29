package Test;

import DataLayer.DBConnection;
import domain.Category;
import domain.CategoryFacade;
import domain.Product;
import domain.Repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFacadeTest {

    private CategoryFacade cf;
    private Category category1;
    private Product p1;
    private Product p2;

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
    void applyCategoryDiscount() {
        assertEquals(category1.getDiscountPercentage(), 0);
        String[] categories = {"Dairy products"};
        LocalDate discountDate1 = LocalDate.of(2024, 07, 07);

        // set first discount
        cf.applyCategoryDiscount(categories, 10, "2024-07-07");
        assertEquals(category1.getDiscountPercentage(), 10);
        assertEquals(category1.getDiscountDate(), discountDate1);

        // set second discount
        LocalDate discountDate2 = LocalDate.of(2024, 07, 10);
        cf.applyCategoryDiscount(categories, 20, "2024-07-10");
        assertEquals(category1.getDiscountPercentage(), 20);
        assertEquals(category1.getDiscountDate(), discountDate2);
    }

    @Test
    void reportDefectiveItem() {
        cf.reportDefectiveItem(p1.getMKT(), 1);
        StringBuilder expected = new StringBuilder();
        expected.append("      Product Name: ").append(p1.getName()).append('\n')
                .append("      MKT: ").append(p1.getMKT()).append('\n')
                .append("      Producer Name: ").append(p1.getProducerName()).append('\n')
                .append("      Total Amount: ").append(1).append('\n')
                .append("      Selling Price: ").append(0.0).append('\n');
        assertEquals(cf.checkDefective(p1.getMKT()), expected.toString());
    }

    @Test
    void checkDefective() {
        assertEquals("No defective items from this product", cf.checkDefective(p1.getMKT()));
    }
}