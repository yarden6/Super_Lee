package BuisnessLayer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

public class ShiftEmployee extends Employee {
    private boolean isFullTime;
    private ArrayList<Role> roles;
    private Stack<Preferences> preferences;
    private int HRid;

    // ---------------constructors---------------------


    public ShiftEmployee(String employeeName, int employeeID, String branch, String bankAccount,
                         boolean isFull, int salary, String password , int HRid,Role role) {
        super(employeeName, employeeID,branch,bankAccount, salary,password);
        this.isFullTime = isFull;
        roles = new ArrayList<>();
        roles.add(role);
        preferences = new Stack<>();
        preferences.add(new Preferences());
        this.HRid = HRid;

    }

    //-----------------methods---------------------------
    public String addRole(Role role){
        if (roles.contains(role))
            return this.getEmployeeName() +" is already " + role;
        else
            roles.add(role);
        return null;
    }

    public String changeRole(Role oldrole, Role newRole){
        if (roles.contains(oldrole)){
            removeRole(oldrole);
            addRole(newRole);
            return null;
        }
        else {
            return this.getEmployeeName() + " isnt a " + oldrole;
        }
    }
    public String removeRole(Role role){
        if(!roles.contains(role))
            return this.getEmployeeName() + " isnt a " + role;
        else
            roles.remove(role);
        return null;
    }

    public String callPreferences(boolean[][] shifts, LocalDate startDate){
        if (shifts.length != 6 || shifts[1].length != 2 || startDate == null )
            return "illegal shifts prefrence";
        preferences.add(new Preferences(shifts,startDate));
        return null;
    }

    public String getLastPref(){
        if (preferences.isEmpty())
            return "no preferences found \n";
        Preferences p = preferences.peek();
        String s = " sun   mon   tue   wen   thu   fri \n";
        boolean [][] shifts = p.getShifts();
        for (int i = 0; i < shifts[i].length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[j][i])
                    s = s + " " +shifts[j][i] +" ";
                else
                    s = s + " " +shifts[j][i];
            }
            s+= " \n";
        }
        return s;
    }
    public String getROles(){
        String s = "";
        for(Role r : roles){
            s = s + r.toString() + ",";
        }
        return s;
    }
    //---------------------getters and setters----------------------------
    public ArrayList<Role> getRoles() {
        return roles;
    }

    public Stack<Preferences> getPreferences() {
        return preferences;
    }

    public int getHRid() {
        return HRid;
    }

}
