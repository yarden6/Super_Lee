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

public class HRManagerTest {
    private HRManager hrManager1;
    private HRManager hrManager2;
    private ShiftEmployee shiftEmployee1;
    private ShiftEmployee shiftEmployee2;
    List<HRManager> hrManagers = new ArrayList<>();
    List<ShiftEmployee> shiftEmployees = new ArrayList<>();
    EmployeeFacade ef;

    @BeforeEach
    public void setUp() {
        hrManager1 = new HRManager("Saar", 1, "1","1",10000,"1");
        hrManager2 = new HRManager("Itamar", 2, "2","2",10000,"2");
        hrManagers.add(hrManager1);
        hrManagers.add(hrManager2);
        shiftEmployee1 = new ShiftEmployee("liron", 3, "1", "3",true, 10000, "3", 1, Role.CASHIER);
        shiftEmployee2 = new ShiftEmployee("Yuval", 4, "2", "4",true, 10000, "4", 2, Role.DELIVERYGUY);
        shiftEmployees.add(shiftEmployee1);
        shiftEmployees.add(shiftEmployee2);
        ef = new EmployeeFacade(hrManagers, shiftEmployees);
        hrManager1.login("1");
        hrManager2.login("2");
    }

    @Test
    public void testHire() {
        assertEquals("employee already exist", ef.hireEmployee(1,"amir", 3, "3",true, 10000, "3", "CASHIER"));
        assertEquals("HR manager not exist", ef.hireEmployee(3,"amir", 4, "4",true, 10000, "4", "DELIVERYGUY"));
        assertNull(ef.hireEmployee(2, "amir", 30, "4", true, 10000, "4", "DELIVERYGUY"));
        assertEquals("amir is already HR manager" ,ef.hireEmployee(2, "amir", 1, "4", true, 10000, "4", "DELIVERYGUY"));
        hrManager1.logout();
        assertEquals(" not logged in", ef.hireEmployee(1, "amir", 30, "4", true, 10000, "4", "DELIVERYGUY"));
    }

    @Test
     public void testFire() {
        assertEquals("",ef.fireEmployee(3, 1));
        assertEquals("employee not exist", ef.fireEmployee(30,1 ));
        assertEquals("not valid HR", ef.fireEmployee(3, 9));
        hrManager1.logout();
        assertEquals(" not logged in", ef.fireEmployee(3, 1));
    }

    @Test
    public void testSetShift(){
        //TODO do all type of tests for this method
    }

    @Test void testGetShifts(){
        assertEquals("not valid HR", ef.getAllShifts(30, LocalDate.now()));
        assertEquals("no shifts found", ef.getAllShifts(1, LocalDate.now()));
        //TODO create shifts and do the test to make is success
        assertEquals("employee doesnt exist", ef.getAllShifts(9, LocalDate.now()));
    }
    @Test
    public void testAddRole() {
        int fakeId = 30;
        String cashier = "CASHIER";
        assertEquals(fakeId + " doesn't exist", ef.addRoleToEmployee(1, 30, "CASHIER"));
        assertEquals(shiftEmployee1.getEmployeeName() + " is already " + cashier , ef.addRoleToEmployee(hrManager1.getID(), shiftEmployee1.getID(), cashier));
        assertEquals("not valid HR", ef.addRoleToEmployee(9, 3, "CASHIER"));
        assertNull(ef.addRoleToEmployee(1, 3, "DELIVERYGUY"));
    }

    @Test
    public void testRemoveRole() {
        int fakeId = 30;
        String storeKeeper = "STOREKEEPER";
        assertEquals(fakeId + " doesn't exist", ef.deleteRoleFromEmployee(1, 30, "CASHIER"));
        assertEquals(shiftEmployee1.getEmployeeName() + " isnt a " + storeKeeper , ef.deleteRoleFromEmployee(hrManager1.getID(), shiftEmployee1.getID(), storeKeeper));
        assertEquals("not valid HR", ef.deleteRoleFromEmployee(9, 3, "CASHIER"));
        assertNull(ef.deleteRoleFromEmployee(1, 3, "CASHIER"));
    }

    @Test
    public void testChangeRole() {
        int fakeId = 30;
        String storeKeeper = "STOREKEEPER";
        String cashier = "CASHIER";
        assertEquals(fakeId + " doesn't exist", ef.changeRoleToEmployee(1, 30, "STOREKEEPER", "CASHIER"));
        assertEquals(shiftEmployee1.getEmployeeName() + " isnt a " + storeKeeper , ef.changeRoleToEmployee(hrManager1.getID(), shiftEmployee1.getID(), storeKeeper, cashier));
        assertEquals("not valid HR", ef.changeRoleToEmployee(9, 3, "STOREKEEPER", "CASHIER"));
        assertNull(ef.changeRoleToEmployee(1, 3, "CASHIER", "DELIVERYGUY"));
    }

    @Test
    public void testUpdateEmployee() {
        assertEquals("this employee is not exist", ef.updateEmployee(new ShiftEmployee("liron", 5, "1", "3",true, 10000, "3", 1, Role.CASHIER), 1));
        assertNull( ef.updateEmployee(new ShiftEmployee("liron", 3, "1", "3",true, 10000, "3", 1, Role.CASHIER), 1));
        assertNull(ef.updateEmployee(new ShiftEmployee("liron", 3, "1", "3",true, 10000, "3", 1, Role.CASHIER), 2));
    }



}