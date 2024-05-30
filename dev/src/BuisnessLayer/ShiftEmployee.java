package BuisnessLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class ShiftEmployee extends Employee {
    private boolean isFullTime;
    private ArrayList<Role> roles;
    private Stack<Preferences> preferences;
    private boolean madePreferences;

    //constructors


//    public ShiftEmployee(String employeeName, int employeeID, String branch, String bankAccount, boolean isFull) {
//        super(employeeID);
//    }

    //methods
    public void addRole(Role role){
        roles.add(role);
    }

    public void changeRole(Role oldrole, Role newRole){
        if (roles.contains(oldrole)){
            removeRole(oldrole);
            addRole(newRole);
        }
        else {
            //TODO (throw e)
        }
    }
    public void removeRole(Role role){
        roles.remove(role);
    }

    public void callPreferences(boolean[][] shifts, Date startDate){
        preferences.add(new Preferences(shifts,startDate));
    }



    //getters and setters
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

    public Stack<Preferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(Stack<Preferences> preferences) {
        this.preferences = preferences;
    }

    public boolean isMadePreferences() {
        return madePreferences;
    }

    public void setMadePreferences(boolean madePreferences) {
        this.madePreferences = madePreferences;
    }
}
