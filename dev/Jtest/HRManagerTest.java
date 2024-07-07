import BuisnessLayer.*;
import BuisnessLayer.Repositories.ShiftEmployeeRepository;
import BuisnessLayer.Repositories.ShiftEmployeeRolesRepository;
import BuisnessLayer.Repositories.ShiftRepository;
import DataLayer.DBConnection;
import org.junit.jupiter.api.AfterAll;
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
    private static HRManager yosef;
    private static EmployeeFacade ef;
    private static ShiftEmployee temp;

    @BeforeEach
    public void setUp() {
        DBConnection.connect("TESTSDB.db");
        ef = new EmployeeFacade();
        yosef = ef.getHRManager(1);
        yosef.login("1");
    }

    @Test
    public void testHireFailEmpExist() {
        assertEquals("employee already exist", ef.hireEmployee(1,"amir", 3,
                "3",true, 10000, "3", "CASHIER", "A"));
        assertEquals("HR manager not exist", ef.hireEmployee(3,"amir", 4,
                "4",true, 10000, "4", "DELIVERYGUY","A"));
        yosef.logout();
        assertEquals(" not logged in", ef.hireEmployee(1, "amir", 30,
                "4", true, 10000, "4", "DELIVERYGUY","A"));
        yosef.login("1");
    }

    @Test
    public void testHireSuccess() {
        assertNull(ef.hireEmployee(1, "naama", 30, "4", true,
                10000, "4", "DELIVERYGUY","A"));
        temp = ef.getShiftEmployee(30);
        ShiftEmployeeRepository.getInstance().delete(temp);
    }


    @Test
    public void testFireFail() {
        assertEquals("employee not exist",ef.fireEmployee(6, 1));
        assertEquals("not valid HR",ef.fireEmployee(2, 3));
    }

    @Test
     public void testFireSuccess() {
        assertEquals("",ef.fireEmployee(3, 1));
        ef.hireEmployee(1, "Itamar", 3, "4", true,
                10000, "4", "DELIVERYGUY","A");
    }


    @Test
    public void testSetShiftFail() {
        Map<Integer, String> shiftRoles = new HashMap<>();
        shiftRoles.put(2, Role.CASHIER.toString());
        shiftRoles.put(4, Role.SHIFTMANAGER.toString());
        int fakeId = 20;
        //test with hrmanager that is not exist
        assertEquals("not valid HR", ef.HRSetShift(fakeId, 4, shiftRoles, LocalDate.now(),
                LocalTime.of(8, 0), LocalTime.of(16, 0), "MORNING"));
        //test with shift manager that is not exist
        assertEquals("shift manager not exist", ef.HRSetShift(1, fakeId, shiftRoles, LocalDate.now(),
                LocalTime.of(8, 0), LocalTime.of(16, 0), "MORNING"));
        //test for shift manager that he is not have shift manager role
        assertEquals("amir isn't a shift manager",
                ef.HRSetShift(1, 2, shiftRoles, LocalDate.now(), LocalTime.of(8, 0),
                        LocalTime.of(16, 0), "MORNING"));
        //test for shift employee that was assign for role he doesn't have
        int idToChange = 5;
        String notHesRole = Role.DELIVERYGUY.toString();
        shiftRoles.put(idToChange, notHesRole);
        assertEquals(idToChange + " was set to be " + notHesRole + " and dont have qualification for it"
                , ef.HRSetShift(1, 4, shiftRoles, LocalDate.of(2024, 6, 9),
                        LocalTime.of(8, 0), LocalTime.of(16, 0), "MORNING"));
        shiftRoles.put(fakeId, Role.CASHIER.toString());
        shiftRoles.replace(5, Role.CASHIER.toString());
        //test for shift employee that is not exist
        assertEquals(fakeId + " not exist", ef.HRSetShift(1, 4, shiftRoles, LocalDate.now(),
                LocalTime.of(8, 0), LocalTime.of(16, 0), "MORNING"));
        shiftRoles.remove(fakeId);
    }

    @Test
    public void testSetShiftSuccess() {
        Map<Integer, String> shiftRoles = new HashMap<>();
        shiftRoles.put(5, Role.CASHIER.toString());
        shiftRoles.put(4, Role.SHIFTMANAGER.toString());
        //test for shift that created good
        assertNull(ef.HRSetShift(1,4,shiftRoles, LocalDate.of(2024,7,8),
                LocalTime.of(8,0), LocalTime.of(16,0), "MORNING"));
    }

    @Test
    void testGetShiftsFail(){
        int fakeId = 20;
        assertEquals("not valid HR", ef.getAllShifts(30, LocalDate.now()));
        assertEquals("no shifts found", ef.getAllShifts(1, LocalDate.of(2024,7,9)));

    }

    @Test
    void testGetShiftsSuccess(){
        String shiftDate= "2024-07-08";
        String shiftTime = "MORNING";
        String shiftStart = "08:00";
        String shiftEnd = "16:00";
        String role = "CASHIER";
        String role2 = "SHIFTMANAGER";
        assertEquals("Date: " + shiftDate+ " " + shiftTime + "  start: " + shiftStart + " end: " + shiftEnd +
                        "  shift manager: Saar \n" +
                        "id: " + 4 + ", Role: " + role2 +"\n" +
                        "id: " + 5 + ", Role: " + role +"\n" ,
                ef.getAllShifts(1, LocalDate.of(2024,7,8)));
    }

//
    @Test
    public void testAddRoleFail() {
        int fakeId = 20;
        assertEquals(fakeId + " doesn't exist", ef.addRoleToEmployee(1, fakeId, "CASHIER"));
        assertEquals("Itamar is already STOREKEEPER", ef.addRoleToEmployee(1, 3, "STOREKEEPER"));
        assertEquals("not valid HR", ef.addRoleToEmployee(9, 30, "CASHIER"));
    }

    @Test
    public void testAddRoleSuccess() {
        assertNull(ef.addRoleToEmployee(1, 3, "CASHIER"));
    }

    @Test
    public void testRemoveRole() {
        int fakeId = 20;
        assertEquals(fakeId + " doesn't exist", ef.deleteRoleFromEmployee(1, fakeId, "CASHIER"));
        assertEquals("Itamar is not a SHIFTMANAGER", ef.deleteRoleFromEmployee(1, 3, "SHIFTMANAGER"));
        assertEquals("not valid HR", ef.deleteRoleFromEmployee(9, 3, "CASHIER"));
    }

    @Test
    public void testRemoveRoleSuccess() {
        assertNull(ef.deleteRoleFromEmployee(1, 3, "CASHIER"));
    }

    @Test
    public void testChangeRole() {
        int fakeId = 20;
        String storeKeeper = "SHIFTMANAGER";
        String cashier = "CASHIER";
        assertEquals(fakeId + " doesn't exist", ef.changeRoleToEmployee(1, fakeId, "STOREKEEPER", "CASHIER"));
        assertEquals("Itamar is not a " + storeKeeper, ef.changeRoleToEmployee(1, 3, storeKeeper, cashier));
        assertEquals("not valid HR", ef.changeRoleToEmployee(9, 3, "STOREKEEPER", "CASHIER"));
    }

    @Test
    public void testChangeRoleSuccess() {
        assertNull(ef.changeRoleToEmployee(1, 3,"STOREKEEPER","CASHIER"));
        ef.changeRoleToEmployee(1, 3,"CASHIER","STOREKEEPER");
    }

@AfterAll
    public static void clean() {
    ef.deleteSfitsFrom(LocalDate.of(2024,7,8));
    }
}