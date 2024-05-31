package BuisnessLayer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class ShiftEmployee extends Employee {
    private boolean isFullTime;
    private ArrayList<Role> roles;
    private Stack<Preferences> preferences;

    // ---------------constructors---------------------


    public ShiftEmployee(String employeeName, int employeeID, String branch, String bankAccount,
                         boolean isFull, int salary,String password) {
        super(employeeName, employeeID,branch,bankAccount, salary,password);
        this.isFullTime = isFull;
        roles = new ArrayList<>();
        preferences = new Stack<>();
    }

    //-----------------methods---------------------------
    public void addRole(Role role){
        if (roles.contains(role))
            throw new IllegalArgumentException(this.getEmployeeName() +" is already " + role);
    }

    public void changeRole(Role oldrole, Role newRole){
        if (roles.contains(oldrole)){
            removeRole(oldrole);
            addRole(newRole);
        }
        else {
            throw new IllegalArgumentException(this.getEmployeeName() + " isnt a " + oldrole);
        }
    }
    public void removeRole(Role role){
        if(!roles.remove(role))
            throw new IllegalArgumentException(this.getEmployeeName() + " isnt a " + role);
    }

    public void callPreferences(boolean[][] shifts, Date startDate){
        if (shifts.length != 7 || shifts[1].length != 2 || startDate == null )
            throw new IllegalArgumentException("illegal shifts prefrence");
        preferences.add(new Preferences(shifts,startDate));
    }

    public Preferences getLastPref(){
        return preferences.peek();
    }

    //---------------------getters and setters----------------------------
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


}
