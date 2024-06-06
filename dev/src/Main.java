import BuisnessLayer.HRManager;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import PresentationLayer.CLI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        HRManager yosef = new HRManager("Yosef",1,"karmiel", "1231",700,"1");
        ShiftEmployee amir = new ShiftEmployee("amir", 2, "karmiel","1231231", true, 300, "2", 1, Role.DELIVERYGUY);
        ShiftEmployee itamar = new ShiftEmployee("Itamar", 3, "karmiel","1231231", true, 300, "2", 1, Role.CASHIER);
        ShiftEmployee saar = new ShiftEmployee("Saar", 4, "karmiel","1231231", true, 300, "4", 1, Role.SHIFTMANAGER);
        ShiftEmployee yuval = new ShiftEmployee("Yuval", 5, "karmiel","1231231", true, 300, "4", 1, Role.STOREKEEPER);
        saar.callPreferences(new boolean[][]{{false,true},{false, true},{true,true},{true,true},{true,true},{true,true}}, LocalDate.now().getDayOfYear()/7+1);
        amir.callPreferences(new boolean[][]{{true,true},{true, true},{true,true},{true,true},{true,true},{true,true}}, LocalDate.now().getDayOfYear()/7+1);
        itamar.callPreferences(new boolean[][]{{true,true},{true, true},{true,true},{true,true},{true,true},{true,true}}, LocalDate.now().getDayOfYear()/7+1);
        yuval.callPreferences(new boolean[][]{{true,true},{true, true},{true,true},{true,true},{true,true},{true,true}}, LocalDate.now().getDayOfYear()/7+1);
        saar.addRole(Role.SHIFTMANAGER);//ID 4
        saar.addRole(Role.DELIVERYGUY);
        itamar.addRole(Role.CASHIER);//ID 3
        amir.addRole(Role.STOREKEEPER);//ID 2
        amir.addRole(Role.DELIVERYGUY);
        yuval.addRole(Role.STOREKEEPER);//ID 5
        yuval.addRole(Role.CASHIER);
        List<HRManager> managers = new ArrayList<>();
        List<ShiftEmployee> employees = new ArrayList<>();
        managers.add(yosef);
        employees.add(amir);
        employees.add(itamar);
        employees.add(saar);
        employees.add(yuval);
        CLI cli = new CLI(managers, employees);

    }
}
