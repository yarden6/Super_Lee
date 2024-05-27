package BuisnessLayer;

import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

public class Shift {
    Employee shiftManager;
    Map<Integer,Role> shiftRoles;
    Date date;
    LocalTime startTime;
    LocalTime endTime;
    Period period;
}
