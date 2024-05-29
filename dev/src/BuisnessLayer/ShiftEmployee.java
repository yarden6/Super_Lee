package BuisnessLayer;

import java.util.ArrayList;

public class ShiftEmployee extends Employee {
    private boolean isFullTime;
    private ArrayList<Role> roles;
    private Preferences prefrences;
    private boolean madePrefrences;

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public Preferences getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(Preferences prefrences) {
        this.prefrences = prefrences;
    }

    public boolean isMadePrefrences() {
        return madePrefrences;
    }

    public void setMadePrefrences(boolean madePrefrences) {
        this.madePrefrences = madePrefrences;
    }
}
