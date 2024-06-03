import BuisnessLayer.HRManager;
import BuisnessLayer.ShiftEmployee;
import PresentationLayer.CLI;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HRManager Yosef = new HRManager("Yosef",1,"karmiel", "1231",700,"1");
        ShiftEmployee amir = new ShiftEmployee("amir", 12, "karmiel","1231231", true, 300, "amir", 1);
        List<HRManager> managers = new ArrayList<>();
        List<ShiftEmployee> employees = new ArrayList<>();
        managers.add(Yosef);
        employees.add(amir);
        CLI cli = new CLI(managers, employees);
    }
}
