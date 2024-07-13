import DataLayer.DBConnectionEM;
import DomainLayer.EM.EmployeeFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class integerationTest {
    private static EmployeeFacade ef;
    @BeforeEach
    public void setUp() {
        DBConnectionEM.connect("TESTSDB.db");
        ef = new EmployeeFacade();
    }
    @Test
    public void testCheckStoreKepper(){
        assertTrue(ef.checkForStoreKeeper(3));
        assertFalse(ef.checkForStoreKeeper(2));
    }

}
