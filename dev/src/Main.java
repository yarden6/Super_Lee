import BuisnessLayer.*;
import BuisnessLayer.Repositories.*;
import DataLayer.DBConnection;
import Library.Pair;
import PresentationLayer.CLI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        startSession();
    }
    public static void startSession(){
        DBConnection.connect("Employees.db");
        CLI cli = new CLI();
    }
}
