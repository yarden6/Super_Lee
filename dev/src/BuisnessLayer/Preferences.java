package BuisnessLayer;

import java.time.LocalDate;

public class Preferences {
    boolean[][] shifts;
    LocalDate madeAt;

    public Preferences(boolean[][] shifts, LocalDate start) {
        //TODO (check if the array of boolean is the right size)
        this.madeAt = start;
        this.shifts = shifts;
    }
    public Preferences(){
        this.madeAt = LocalDate.now();
        this.shifts = new boolean[6][2];
    }

    public LocalDate getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(LocalDate madeAt) {
        this.madeAt = madeAt;
    }

    public boolean[][] getShifts() {
        return shifts;
    }

    public void setShifts(boolean[][] shifts) {
        this.shifts = shifts;
    }
}
