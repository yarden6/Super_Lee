import BuisnessLayer.*;
import BuisnessLayer.Repositories.HRManagerRepository;
import BuisnessLayer.Repositories.PreferencesRepository;
import BuisnessLayer.Repositories.ShiftEmployeeRepository;
import BuisnessLayer.Repositories.ShiftEmployeeRolesRepository;
import DataLayer.DBConnection;
import Library.Pair;
import PresentationLayer.CLI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        startSession();
    }
    public static void startSession(){
        DBConnection.connect("Employees.db");
//        HRManagerRepository hrManagerRepository = HRManagerRepository.getInstance();
//        ShiftEmployeeRepository shiftEmployeeRepository = ShiftEmployeeRepository.getInstance();
//        PreferencesRepository preferencesRepository = PreferencesRepository.getInstance();
//        ShiftEmployeeRolesRepository shiftEmployeeRolesRepository = ShiftEmployeeRolesRepository.getInstance();
//        HRManager yosef = new HRManager("Yosef",1,"karmiel",
//                "1",700,"1");
//        hrManagerRepository.add(yosef);
//        ShiftEmployee amir = new ShiftEmployee("amir", 2, "karmiel",
//                "2", true, 300, "2", 1, Role.DELIVERYGUY, Vehicle.A);
//        ShiftEmployee itamar = new ShiftEmployee("Itamar", 3, "karmiel",
//                "3", true, 300, "3", 1, Role.CASHIER,Vehicle.A);
//        ShiftEmployee saar = new ShiftEmployee("Saar", 4, "karmiel",//id 4 shiftmanager
//                "4", true, 300, "4", 1, Role.SHIFTMANAGER,Vehicle.A);
//        ShiftEmployee yuval = new ShiftEmployee("Yuval", 5, "karmiel",
//                "5", true, 300, "5", 1, Role.STOREKEEPER,Vehicle.A);
//        List<HRManager> managers = new ArrayList<>();
//        List<ShiftEmployee> employees = new ArrayList<>();
//        managers.add(yosef);
//        employees.add(amir);
//        employees.add(itamar);
//        employees.add(saar);
//        employees.add(yuval);
//        boolean[][] alltrue = { {true, true}, {true, true}, {true, true}, {true, true}, {true, true}, {true, true}};
//        for (ShiftEmployee employee : employees) {
//            shiftEmployeeRepository.add(employee);
//            for (Preferences preference : employee.getPreferences()) {
//                preferencesRepository.add(preference);
//            }
//            shiftEmployeeRolesRepository.add(new Pair<>(employee.getID(), employee.getRoles().get(0)));
//            employee.callPreferences(alltrue, LocalDate.now().getDayOfYear() / 7 + 1);
//        }
//        saar.addRole(Role.SHIFTMANAGER);
//        itamar.addRole(Role.STOREKEEPER);//ID 3 storekeeper,cashier
//        amir.addRole(Role.STOREKEEPER);//ID 2 deliveryguy storekeeper
//        yuval.addRole(Role.CASHIER); // id 5 storekeeper cashier
        CLI cli = new CLI();
    }
}
