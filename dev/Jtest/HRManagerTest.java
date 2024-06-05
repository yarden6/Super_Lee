import BuisnessLayer.EmployeeFacade;
import BuisnessLayer.HRManager;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HRManagerTest {
    private HRManager hrManager1;
    private HRManager hrManager2;
    private ShiftEmployee shiftEmployee1;
    private ShiftEmployee shiftEmployee2;
    private ShiftEmployee shiftEmployee3;
    private ShiftEmployee shiftEmployee4;
    private List<HRManager> hrManagers = new ArrayList<>();
    private List<ShiftEmployee> shiftEmployees = new ArrayList<>();
    private EmployeeFacade ef;

    @BeforeEach
    public void setUp() {
        hrManager1 = new HRManager("Saar", 1, "1","1",10000,"1");
        hrManager2 = new HRManager("Itamar", 2, "2","2",10000,"2");
        hrManagers.add(hrManager1);
        hrManagers.add(hrManager2);
        shiftEmployee1 = new ShiftEmployee("jordan", 3, "1", "3",true, 10000, "3", 1, Role.CASHIER);
        shiftEmployee2 = new ShiftEmployee("Yuval", 4, "2", "4",true, 10000, "4", 2, Role.DELIVERYGUY);
        shiftEmployees.add(shiftEmployee1);
        shiftEmployees.add(shiftEmployee2);
        shiftEmployee3 = new ShiftEmployee("amir", 5, "2", "5",true, 10000, "3", 2, Role.CASHIER);
        shiftEmployee4 = new ShiftEmployee("bar", 6, "2", "6",true, 10000, "3", 2, Role.SHIFTMANAGER);
        shiftEmployees.add(shiftEmployee3);
        shiftEmployees.add(shiftEmployee4);
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
        Map<Integer,String> shiftRoles = new HashMap<>();
        shiftRoles.put(4, Role.DELIVERYGUY.toString());
        shiftRoles.put(5, Role.CASHIER.toString());
        int fakeId = 30;
        //test with hrmanager that is not exist
        assertEquals("not valid HR", ef.HRSetShift(fakeId,shiftEmployee3.getID(),shiftRoles, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
        //test with shift manager that is not exist
        assertEquals("shift manager not exist", ef.HRSetShift(2,fakeId,shiftRoles, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
        //test for shift manager that he is not have shift manager role
        assertEquals(shiftEmployee2.getEmployeeName() + " isn't a shift manager", ef.HRSetShift(2,shiftEmployee2.getID(),shiftRoles,LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
        //test for shift employee that was assign for role he doesn't have
        int idToChange = 5;
        String notHesRole = Role.STOREKEEPER.toString();
        shiftRoles.replace(idToChange,notHesRole);
        assertEquals(idToChange + " was set to be " + notHesRole + " and dont have qualification for it" ,ef.HRSetShift(2,shiftEmployee4.getID(),shiftRoles, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
        shiftRoles.put(fakeId, Role.CASHIER.toString());
        shiftRoles.replace(5, Role.CASHIER.toString());
        //test for shift employee that is not exist
        assertEquals(fakeId + " not exist", ef.HRSetShift(2,shiftEmployee3.getID(),shiftRoles, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
        shiftRoles.remove(fakeId);
        //test for shift that created good
        assertNull(ef.HRSetShift(2,shiftEmployee4.getID(),shiftRoles, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));



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