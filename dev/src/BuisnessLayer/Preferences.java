package BuisnessLayer;

import java.time.LocalDate;

public class Preferences {
    boolean[][] shifts;
    int MadeAtWeek;

    public Preferences(boolean[][] shifts, int start) {
        //TODO (check if the array of boolean is the right size)
        this.MadeAtWeek = start;
        this.shifts = shifts;
    }
    public Preferences(){
        this.MadeAtWeek = LocalDate.now().getDayOfYear()/7;
        this.shifts = new boolean[6][2];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++){
                shifts[i][j] = true;
            }
        }
    }

    public int getMadeAtWeek() {
        return MadeAtWeek;
    }

    public void setMadeAtWeek(int madeAtWeek) {
        this.MadeAtWeek = madeAtWeek;
    }

    public boolean[][] getShifts() {
        return shifts;
    }

    public void setShifts(boolean[][] shifts) {
        this.shifts = shifts;
    }
}
