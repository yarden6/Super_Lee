package BuisnessLayer;

import java.util.Date;

public class Preferences {


    boolean[][] shifts;
    Date start;
    Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean[][] getShifts() {
        return shifts;
    }

    public void setShifts(boolean[][] shifts) {
        this.shifts = shifts;
    }
}
