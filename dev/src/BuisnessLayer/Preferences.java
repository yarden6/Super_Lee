package BuisnessLayer;

import java.util.Date;

public class Preferences {
    boolean[][] shifts;
    Date start;

    public Preferences(boolean[][] shifts, Date start) {
        //TODO (check if the array of boolean is the right size)
        this.start = start;
        this.shifts = shifts;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public boolean[][] getShifts() {
        return shifts;
    }

    public void setShifts(boolean[][] shifts) {
        this.shifts = shifts;
    }
}
