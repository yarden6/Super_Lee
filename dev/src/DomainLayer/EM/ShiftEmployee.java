package DomainLayer.EM;

import DomainLayer.EM.Repositories.*;
import Library.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

public class ShiftEmployee extends Employee {
    private boolean isFullTime;
    private ArrayList<Role> roles;
    private Stack<Preferences> preferences;
    private int HRid;
    private Vehicle license;
    private PreferencesRepository preferencesRepository = PreferencesRepository.getInstance();
    ShiftEmployeeRolesRepository shiftEmployeeRolesRepository = ShiftEmployeeRolesRepository.getInstance();
    // ---------------constructors---------------------


    public ShiftEmployee(String employeeName, int employeeID, String branch, String bankAccount,
                         boolean isFull, int salary, String password , int HRid,Role role,Vehicle license) {
        super(employeeName, employeeID,branch,bankAccount, salary,password);
        this.isFullTime = isFull;
        roles = new ArrayList<>();
        roles.add(role);
        preferences = new Stack<>();
        preferences.push(new Preferences(getID()));
        preferences.push(new Preferences(new boolean[6][2], LocalDate.now().getDayOfYear()/7 + 1, getID()));
        this.HRid = HRid;
        this.license = license;

    }

    public ShiftEmployee(int employeeID, String employeeName, String branch, String bankAccount, int salary,
                         LocalDate startDate, LocalDate resignationDate, int vacationDays, String password,
                         boolean isFullTime, ArrayList<Role> roles, Stack<Preferences> preferences, int HRid, Vehicle license) {
        super(employeeID, employeeName, branch, bankAccount, salary, startDate, resignationDate, vacationDays, password);
        this.isFullTime = isFullTime;
        this.roles = roles;
        this.preferences = preferences;
        this.HRid = HRid;
        this.license = license;
    }

    //-----------------methods---------------------------
    public String addRole(Role role){
        if (roles.contains(role))
            return this.getEmployeeName() +" is already " + role;
        else{
            roles.add(role);
            //------------sql-------------
            shiftEmployeeRolesRepository.add(new Pair<>(getID(),role));
            //------------sql-------------
        }
        return null;
    }

    public String changeRole(Role oldrole, Role newRole){
        if (roles.contains(oldrole)){
            removeRole(oldrole);
            addRole(newRole);
            return null;
        }
        else {
            return this.getEmployeeName() + " is not a " + oldrole;
        }
    }
    public String removeRole(Role role){
        if(!roles.contains(role))
            return this.getEmployeeName() + " is not a " + role;
        else{
            roles.remove(role);
            //------------sql-------------
            shiftEmployeeRolesRepository.delete(new Pair<>(getID(),role));
            //------------sql-------------
        }
        return null;
    }

    public String callPreferences(boolean[][] shifts, int startDate){
        if (shifts.length != 6 || shifts[1].length != 2  )
            return "illegal shifts preference";
        Preferences newPref = new Preferences(shifts,startDate, getID());
        if (preferences.peek().getMadeAtWeek() == startDate) {
            preferences.pop();
            //------------sql-------------
            preferencesRepository.update(newPref);
            //------------sql-------------
        }
        else{
            //------------sql-------------
            preferencesRepository.add(newPref);
            //------------sql-------------
        }
        preferences.push(newPref);

        return null;
    }

    public String getLastPref(int index){
        if (preferences.isEmpty())
            return "no preferences found \n";
        Preferences p = preferences.get(index);
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

    public Vehicle getLicense() {
        return license;
    }

    public boolean isFullTime() {
        return isFullTime;
    }


}
