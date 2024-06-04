package Test;

import domain.CategoryFacade;
import domain.ReportFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportFacadeTest {
    CategoryFacade cf;
    ReportFacade rf;
    @BeforeEach
    void setUp() {
        cf = new CategoryFacade();
        cf.loadData();
        rf = new ReportFacade(cf);
    }

    @Test
    void makeInventoryReport() {
        String expected = "Main Category: Dairy products\n" +
                "  Sub Category: Cheese\n" +
                "    Sub-Sub Category: 100g\n" +
                "      Product Name: Cheddar Cheese 100g\n" +
                "      MKT: 2002\n" +
                "      Producer Name: Cheese Maker\n" +
                "      Total Amount: 0\n" +
                "      Selling Price: 3.99\n" +
                "\n" +
                "  Sub Category: Cream\n" +
                "  Sub Category: Milk\n" +
                "    Sub-Sub Category: 1000ml\n" +
                "     No products\n" +
                "    Sub-Sub Category: 500ml\n" +
                "      Product Name: milk 500ml\n" +
                "      MKT: 456\n" +
                "      Producer Name: Tara\n" +
                "      Total Amount: 0\n" +
                "      Selling Price: 7.5\n" +
                "\n" +
                "      Product Name: milk 500ml\n" +
                "      MKT: 123\n" +
                "      Producer Name: Tnuva\n" +
                "      Total Amount: 20\n" +
                "      Selling Price: 6.5\n" +
                "\n" +
                "Main Category: Shower products\n" +
                "  Sub Category: Shampoo\n" +
                "    Sub-Sub Category: 750ml\n" +
                "      Product Name: Herbal Shampoo 750ml\n" +
                "      MKT: 3001\n" +
                "      Producer Name: Shampoo Inc.\n" +
                "      Total Amount: 0\n" +
                "      Selling Price: 5.49\n" +
                "\n" +
                "  Sub Category: Conditioner\n" +
                "    Sub-Sub Category: 750ml\n" +
                "      Product Name: Silky Conditioner 750ml\n" +
                "      MKT: 3002\n" +
                "      Producer Name: Conditioner Co.\n" +
                "      Total Amount: 0\n" +
                "      Selling Price: 4.99\n" +
                "\n" +
                "Main Category: Baking products\n" +
                "  Sub Category: Dark Chocolate\n" +
                "    Sub-Sub Category: 100g\n" +
                "      Product Name: Premium Dark Chocolate\n" +
                "      MKT: 1001\n" +
                "      Producer Name: Top Producer\n" +
                "      Total Amount: 20\n" +
                "      Selling Price: 2.99\n" +
                "\n" +
                "  Sub Category: Milk Chocolate\n" +
                "    Sub-Sub Category: 100g\n" +
                "     No products";
        assertEquals(rf.makeInventoryReport(new String[]{""}),expected);

    }

    @Test
    void makeDefectiveReport() {
    }

}