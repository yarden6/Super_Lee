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
        shiftEmployee2 = new ShiftEmployee("Itamar", 3, "1", "4",true, 10000, "3", 1, Role.SHIFTMANAGER);
        shiftEmployee3 = new ShiftEmployee("Yuval", 4, "1", "4",true, 10000, "3", 1, Role.DELIVERYGUY);
        shiftEmployees.add(shiftEmployee1);
        shiftEmployees.add(shiftEmployee2);
        shiftEmployees.add(shiftEmployee3);
        ef = new EmployeeFacade(hrManagers, shiftEmployees);
    }

    @Test
    public void testSetPreferences() {
        boolean[][] invalidPreferences = {{true, false}, {true, false}, {true, false}, {true, false}, {true, false}, {true, false}, {true, false}};
        boolean[][] validPreferences = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, true}};
        assertEquals("employee not logged in", ef.makePreferences(2, validPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52));
        shiftEmployee1.login("2");
        assertEquals("illegal shifts preference", ef.makePreferences(2, invalidPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52));
        assertNull(ef.makePreferences(2, validPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52));

    }

    @Test
    public void testGetPreferences() {
        int fakeId = 30;
        boolean[][] validPreferences = {{true, false}, {false, true}, {true, true}, {false, true}, {false, false}, {true, true}};
        String days = " sun   mon   tue   wen   thu   fri ";
        String thisWeekPref = "this week preferences : ";
        String thisWeekMorning = " true  true  true  true  true  true  ";
        String thisWeekEvening = " true  true  true  true  true  true  ";
        String nextWeekPref = "next week preferences : ";
        String nextWeekMorning = " true  false true  false false true  ";
        String nextWeekEvening = " false true  true  true  false true  ";
        assertEquals("employee not exist", ef.getLastPref(fakeId));
        assertEquals("not logged in", ef.getLastPref(2));
        shiftEmployee1.login("2");
        ef.makePreferences(2, validPreferences, (LocalDate.now().getDayOfYear()/7 + 1)% 52);
        assertEquals(thisWeekPref +"\n" + days + "\n" + thisWeekMorning +"\n" + thisWeekEvening +"\n\n"
                    + nextWeekPref + "\n" +days + "\n" + nextWeekMorning + "\n" + nextWeekEvening + "\n", ef.getLastPref(2));
    }

    @Test
    public void testGetShifts() {
        int fakeId = 30;
        String shiftDate= LocalDate.now().toString();
        String shiftTime = "MORNING";
        String shiftStart = "08:00";
        String shiftEnd = "16:00";
        String role = "CASHIER";
        boolean[][] allweekTrue = {{true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, true}};
        assertEquals("Employee not exist", ef.getShifts(fakeId, LocalDate.now()));
        assertEquals("not logged in", ef.getShifts(2, LocalDate.now()));
        shiftEmployee1.login("2");
        assertEquals("no shifts found", ef.getShifts(2, LocalDate.now()));
        hrManager1.login("1");
        Map<Integer,String> shiftRoles = new HashMap<>();
        shiftRoles.put(4, Role.DELIVERYGUY.toString());
        shiftRoles.put(2, Role.CASHIER.toString());
        shiftEmployee1.callPreferences(allweekTrue, (LocalDate.now().getDayOfYear()/7 + 1)% 52);
        shiftEmployee2.callPreferences(allweekTrue, (LocalDate.now().getDayOfYear()/7 + 1)% 52);
        shiftEmployee3.callPreferences(allweekTrue, (LocalDate.now().getDayOfYear()/7 + 1)% 52);
        ef.HRSetShift(1,3, shiftRoles , LocalDate.now(), LocalTime.of(8, 0), LocalTime.of(16, 0), "MORNING");
        assertEquals("Date: " + shiftDate + " " + shiftTime + "  start: " + shiftStart + " end: " + shiftEnd
                    + "  shift manager: " + shiftEmployee2.getEmployeeName() + " | Role: " + role + "\n", ef.getShifts(2, LocalDate.now()));
    }
}
