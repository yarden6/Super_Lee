package DataLayer;

import BuisnessLayer.EmployeeFacade;
import BuisnessLayer.HRManager;
import BuisnessLayer.Shift;
import BuisnessLayer.ShiftEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HRManagerDao implements Dao<HRManager> {

    private Connection connection;

    public HRManagerDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<HRManager> getAll() {
        List<HRManager> hrManagers = new ArrayList<>();
        String query = "SELECT e.employeeID, e.employeeName, e.branch, e.bankAccount, e.salary, " +
                       "e.startDate, e.resignationDate, e.vacationDays, e.isLoggedIn, e.password " +
                       "FROM HRManager h " +
                       "JOIN Employee e ON h.employeeID = e.employeeID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                String employeeName = resultSet.getString("name");
                int employeeId = resultSet.getInt("employeeID");
                String branch = resultSet.getString("branch");
                String bankAccount = resultSet.getString("bankAccount");
                int salary = resultSet.getInt("salary");
                String password = resultSet.getString("password");


                HRManager hrManager = new HRManager(employeeName, employeeId, branch, bankAccount, salary, password);
                //TODO - add all shift employees assign to this HRManager
                hrManagers.add(hrManager);
            }
        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return hrManagers;
    }





    @Override
    public void create(HRManager hrManager) {
        String query = "INSERT INTO HRManager(employeeID) VALUES(" + hrManager.getID() + ")";
        String query2 = "INSERT INTO Employee(employeeID, employeeName, branch, bankAccount, salary, startDate, resignationDate, vacationDays, isLoggedIn, password) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement statement2 = connection.prepareStatement(query2)) {
            statement.setInt(1, hrManager.getID());
            statement2.setInt(1, hrManager.getID());
            statement2.setString(2, hrManager.getEmployeeName());
            statement2.setString(3, hrManager.getBranch());
            statement2.setString(4, hrManager.getBanckAccount());
            statement2.setInt(5, hrManager.getSalary());
            statement2.setDate(6, hrManager.getStartDate());
            statement2.setDate(7, hrManager.getResignationDate());
            statement2.setInt(8, hrManager.getVacationDays());
            statement2.setBoolean(9, hrManager.isLoggedIn());
            statement2.setString(10, hrManager.getPassword());

            statement.executeUpdate();
            statement2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(HRManager hrManager) {}

    @Override
    public void delete(HRManager hrManager) {}
}
