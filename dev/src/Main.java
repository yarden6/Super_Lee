import BuisnessLayer.HRManager;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import BuisnessLayer.Vehicle;
import DataLayer.DBConnection;
import PresentationLayer.CLI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        startSession();
    }
    public static void startSession(){
        DBConnection.connect("Employees.db");
        HRManager yosef = new HRManager("Yosef",1,"karmiel",
                "1",700,"1");
//        ShiftEmployee amir = new ShiftEmployee("amir", 2, "karmiel",
//                "2", true, 300, "2", 1, Role.DELIVERYGUY, Vehicle.A);
//        ShiftEmployee itamar = new ShiftEmployee("Itamar", 3, "karmiel",
//                "3", true, 300, "3", 1, Role.CASHIER,Vehicle.A);
//        ShiftEmployee saar = new ShiftEmployee("Saar", 4, "karmiel",//id 4 shiftmanager
//                "4", true, 300, "4", 1, Role.SHIFTMANAGER,Vehicle.A);
//        ShiftEmployee yuval = new ShiftEmployee("Yuval", 5, "karmiel",
//                "5", true, 300, "5", 1, Role.STOREKEEPER,Vehicle.A);
//        itamar.addRole(Role.STOREKEEPER);//ID 3 storekeeper,cashier
//        amir.addRole(Role.STOREKEEPER);//ID 2 deliveryguy storekeeper
//        yuval.addRole(Role.CASHIER); // id 5 storekeeper cashier
        List<HRManager> managers = new ArrayList<>();
        List<ShiftEmployee> employees = new ArrayList<>();
        managers.add(yosef);
//        employees.add(amir);
//        employees.add(itamar);
//        employees.add(saar);
//        employees.add(yuval);
        CLI cli = new CLI(managers, employees);
    }
}
