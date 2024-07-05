package DataLayer;

import BuisnessLayer.HRManager;

import java.sql.*;
import java.time.LocalDate;
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
        String query = "SELECT EMPLOYEEID, EMPLOYEENAME, BRANCH, BANKACCOUNT, SALARY, STARTDATE, RESIGNATIONDATE," +
                       "VACATIONDAYS, ISLOGGEDIN, PASSWORD" +
                       "FROM HRMANAGER";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                String employeeName = resultSet.getString("EMPLOYEENAME");
                int employeeId = resultSet.getInt("EMPLOYEEID");
                String branch = resultSet.getString("BRANCH");
                String bankAccount = resultSet.getString("BANKACCOUNT");
                int salary = resultSet.getInt("SALARY");
                LocalDate startDate = resultSet.getDate("STARTDATE").toLocalDate();
                LocalDate resignationDate = resultSet.getDate("RESIGNATIONDATE").toLocalDate();
                int vacationDays = resultSet.getInt("VACATIONDAYS");
                boolean isLoggedIn = resultSet.getBoolean("ISLOGGEDIN");
                String password = resultSet.getString("PASSWORD");


                HRManager hrManager = new HRManager(employeeId, employeeName, branch, bankAccount, salary,
                        startDate, resignationDate, vacationDays, password);
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
        String query = "INSERT INTO HRMANAGER(EMPLOYEEID, EMPLOYEENAME, BRANCH, BANKACCOUNT, SALARY, STARTDATE," +
                       "RESIGNATIONDATE, VACATIONDAYS, ISLOGGEDIN, PASSWORD) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hrManager.getID());
            statement.setInt(1, hrManager.getID());
            statement.setString(2, hrManager.getEmployeeName());
            statement.setString(3, hrManager.getBranch());
            statement.setString(4, hrManager.getBankAccount());
            statement.setInt(5, hrManager.getSalary());
            statement.setDate(6, Date.valueOf(hrManager.getStartDate()));
            statement.setDate(7, Date.valueOf(hrManager.getResignationDate()));
            statement.setInt(8, hrManager.getVacationDays());
            statement.setBoolean(9, hrManager.isLoggedIn());
            statement.setString(10, hrManager.getPassword());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(HRManager hrManager) {}

    @Override
    public void delete(HRManager hrManager) {}
}
