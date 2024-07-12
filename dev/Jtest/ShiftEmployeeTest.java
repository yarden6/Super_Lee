
import DataLayer.DBConnectionEM;
import DomainLayer.EM.*;
import DomainLayer.EM.Repositories.ShiftEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ShiftEmployeeTest {
    private EmployeeFacade ef;
    private HRManager yosef;

    @BeforeEach
    public void setUp() {
        DBConnectionEM.connect("TESTSDB.db");
        ef = new EmployeeFacade();
        yosef = ef.getHRManager(1);
        yosef.login("1");
    }

    @Test
    public void testSetPreferencesFail() {
        assertNull(ef.hireEmployee(1, "naama", 30, "4", true,
                10000, "30", "DELIVERYGUY","A"));
        ShiftEmployee temp = ef.getShiftEmployee(30);
        boolean[][] invalidPreferences = {{true, false}, {true, false}, {true, false}, {true, false}, {true, false}, {true, false}, {true, false}};
        boolean[][] validPreferences = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, true}};
        assertEquals("employee not logged in", ef.makePreferences(2, validPreferences, (LocalDate.now().getDayOfYear() / 7 + 1) % 52));
        temp.login("30");
        assertEquals("illegal shifts preference", ef.makePreferences(temp.getID(), invalidPreferences, (LocalDate.now().getDayOfYear() / 7 + 1) % 52));
        ShiftEmployeeRepository.getInstance().delete(temp);
    }

    @Test
    public void testSetPreferencesSuccess() {
        ef.hireEmployee(1, "naama", 31, "4", true,
                10000, "31", "DELIVERYGUY","A");
        ShiftEmployee temp = ef.getShiftEmployee(31);
        temp.login("31");
        boolean[][] validPreferences = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, true}};
        assertNull(ef.makePreferences(temp.getID(), validPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52));
        ShiftEmployeeRepository.getInstance().delete(temp);
    }
//
    @Test
    public void testGetPreferencesFail() {
        int fakeId = 20;
        assertEquals("employee not exist", ef.getLastPref(fakeId));
        assertEquals("not logged in", ef.getLastPref(2));
    }

    @Test
    public void testGetPreferencesSuccess() {
        ef.hireEmployee(1, "naama", 32, "4", true,
                10000, "32", "DELIVERYGUY","A");
        ShiftEmployee temp = ef.getShiftEmployee(32);
        temp.login("32");
        boolean[][] validPreferences = {{true, false}, {false, true}, {true, true}, {false, true}, {false, false}, {true, true}};
        String days = " sun   mon   tue   wen   thu   fri ";
        String thisWeekPref = "this week preferences : ";
        String thisWeekMorning = " true  true  true  true  true  true  ";
        String thisWeekEvening = " true  true  true  true  true  true  ";
        String nextWeekPref = "next week preferences : ";
        String nextWeekMorning = " true  false true  false false true  ";
        String nextWeekEvening = " false true  true  true  false true  ";
        ef.makePreferences(temp.getID(), validPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52);
        assertEquals(thisWeekPref +"\n" + days + "\n" + thisWeekMorning +"\n" + thisWeekEvening +"\n\n"
                    + nextWeekPref + "\n" +days + "\n" + nextWeekMorning + "\n" + nextWeekEvening + "\n", ef.getLastPref(temp.getID()));
        ShiftEmployeeRepository.getInstance().delete(temp);
    }

    @Test
    public void testGetShiftsFail() {
        int fakeId = 20;
        ShiftEmployee shiftEmployee1 = ef.getShiftEmployee(5);
        assertEquals("Employee not exist", ef.getShifts(fakeId, LocalDate.now()));
        assertEquals("not logged in", ef.getShifts(2, LocalDate.now()));
        shiftEmployee1.login("5");
        assertEquals("no shifts found", ef.getShifts(5, LocalDate.of(2024, 7, 8)));
    }

    @Test
    public void testGetShiftsSuccess() {
        String shiftDate= LocalDate.of(2024, 7, 7).toString();
        String shiftTime = "MORNING";
        String shiftStart = "04:00";
        String shiftEnd = "09:00";
        String role = "STOREKEEPER";
        String role2 = "SHIFTMANAGER";
        ShiftEmployee shiftManager = ef.getShiftEmployee(4);
        ShiftEmployee shiftEmployee2 = ef.getShiftEmployee(5);
        shiftEmployee2.login("5");
        assertEquals("Date: " + shiftDate + " " + shiftTime + "  start: " + shiftStart + " end: " + shiftEnd
                    + "  shift manager: " + shiftManager.getEmployeeName()
                + " | Role: " + role + "\n"
                , ef.getShifts(5, LocalDate.of(2024, 7, 7)));
    }
}
