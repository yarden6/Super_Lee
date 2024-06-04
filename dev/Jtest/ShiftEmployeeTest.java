import BuisnessLayer.EmployeeFacade;
import BuisnessLayer.HRManager;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class ShiftEmployeeTest {
    private HRManager hrManager1;
    private ShiftEmployee shiftEmployee1;
    private ShiftEmployee shiftEmployee2;
    private ShiftEmployee shiftEmployee3;
    List<HRManager> hrManagers = new ArrayList<>();
    List<ShiftEmployee> shiftEmployees = new ArrayList<>();
    EmployeeFacade ef;

    @BeforeEach
    public void setUp() {
        hrManager1 = new HRManager("Saar", 1, "1","1",10000,"1");
        hrManagers.add(hrManager1);
        shiftEmployee1 = new ShiftEmployee("liron", 2, "1", "3",true, 10000, "2", 1, Role.CASHIER);
        shiftEmployee2 = new ShiftEmployee("Itamar", 3, "2", "4",true, 10000, "3", 1, Role.DELIVERYGUY);
        shiftEmployee3 = new ShiftEmployee("Yuval", 4, "2", "4",true, 10000, "3", 1, Role.DELIVERYGUY);
        shiftEmployees.add(shiftEmployee1);
        shiftEmployees.add(shiftEmployee2);
        shiftEmployees.add(shiftEmployee3);
        ef = new EmployeeFacade(hrManagers, shiftEmployees);
        //TODO create preferences to all employees
        //TODO create shifts to all employees
    }

    @Test
    public void testSetPreferences() {
        //TODO test setPreferences
    }

    @Test
    public void testGetPreferences() {
        //TODO test getPreferences
    }

    @Test
    public void testGetShifts() {
        //TODO test getShifts
    }
}
