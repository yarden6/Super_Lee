import DataLayer.DBConnection;
import DataLayer.DBConnectionEM;
import PresentationLayer.CLI;


public class Main {
    public static void main(String[] args) {
        startSession();
    }
    public static void startSession(){
        DBConnectionEM.connect("Employees.db");
        DBConnection.connect("SuperLee.db");
        CLI cli = new CLI();
    }
}
