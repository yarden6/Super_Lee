package DataLayer;

import BuisnessLayer.Preferences;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import BuisnessLayer.Vehicle;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class ShiftEmployeeDao implements Dao<ShiftEmployee> {
    private Connection connection;

    public ShiftEmployeeDao() {
        this.connection = DBConnection.getConnection();
    }


    @Override
    public List<ShiftEmployee> getAll() {
        List<ShiftEmployee> shiftEmployees = new ArrayList<>();
        String query = "SELECT * FROM SHIFTEMPLOYEE";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int employeeId = resultSet.getInt("EMPLOYEEID");
                String employeeName = resultSet.getString("EMPLOYEENAME");
                String branch = resultSet.getString("BRANCH");
                String bankAccount = resultSet.getString("BANKACCOUNT");
                int salary = resultSet.getInt("SALARY");
                LocalDate startDate = resultSet.getDate("STARTDATE").toLocalDate();
                Date resignationDateValue = resultSet.getDate("RESIGNATIONDATE");
                LocalDate resignationDate = resignationDateValue != null ? resignationDateValue.toLocalDate() : null;
                int vacationDays = resultSet.getInt("VACATIONDAYS");
                boolean isLoggedIn = resultSet.getBoolean("ISLOGGEDIN");
                String password = resultSet.getString("PASSWORD");
                boolean isFullTime = resultSet.getBoolean("ISFULLTIME");
                int HRid = resultSet.getInt("HRID");
                String license = resultSet.getString("LICENSE");
                ArrayList<Role> roles = getRolesByEmployeeId(employeeId);
                ShiftEmployee shiftEmployee = new ShiftEmployee(employeeId, employeeName, branch, bankAccount, salary,
                        startDate, resignationDate, vacationDays, password, isFullTime, roles, new Stack<Preferences>(), HRid
                        , Vehicle.valueOf(license));
                shiftEmployees.add(shiftEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shiftEmployees;
    }

    private ArrayList<Role> getRolesByEmployeeId(int employeeId) {
        ArrayList<Role> roles = new ArrayList<>();
        String query = "SELECT SHIFTEMPLOYEEROLE " +
                "FROM SHIFTEMPLOYEEROLE " +
                "WHERE SHIFTEMPLOYEEID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String roleName = resultSet.getString("SHIFTEMPLOYEEROLE");
                Role role = Role.valueOf(roleName); // Assuming Role has a constructor that takes a roleName
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public List<Preferences> getPreferencesByEmployeeId(int employeeId) {
        List<Preferences> preferences = new ArrayList<>();
        String query = "SELECT * FROM Preferences WHERE EMPLOYEEROLE = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int madeAtWeek = resultSet.getInt("MadeAtWeek");
                boolean[][] shifts = new boolean[6][2];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 2; j++) {
                        String columnName = "bool" + (i + 1) + (j + 1);
                        shifts[i][j] = resultSet.getBoolean(columnName);
                    }
                }

                Preferences preference = new Preferences(shifts, madeAtWeek, employeeId);
                preferences.add(preference);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preferences;
    }

    @Override
    public void create(ShiftEmployee shiftEmployee) {
        String query = "INSERT INTO SHIFTEMPLOYEE (EMPLOYEEID, EMPLOYEENAME, BRANCH, BANKACCOUNT, SALARY, " +
                "STARTDATE, RESIGNATIONDATE, VACATIONDAYS, ISLOGGEDIN, PASSWORD, ISFULLTIME, HRID, LICENSE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shiftEmployee.getID());
            statement.setString(2, shiftEmployee.getEmployeeName());
            statement.setString(3, shiftEmployee.getBranch());
            statement.setString(4, shiftEmployee.getBankAccount()); // Note: There's a typo in getBanckAccount(), consider renaming to getBankAccount()
            statement.setInt(5, shiftEmployee.getSalary());
            statement.setDate(6, java.sql.Date.valueOf(shiftEmployee.getStartDate()));
            statement.setDate(7, shiftEmployee.getResignationDate() != null ? java.sql.Date.valueOf(shiftEmployee.getResignationDate()) : null);
            statement.setInt(8, shiftEmployee.getVacationDays());
            statement.setBoolean(9, shiftEmployee.isLoggedIn());
            statement.setString(10, shiftEmployee.getPassword());
            statement.setBoolean(11, shiftEmployee.isFullTime());
            statement.setInt(12, shiftEmployee.getHRid());
            statement.setString(13, shiftEmployee.getLicense().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ShiftEmployee shiftEmployee) {
        String query = "UPDATE SHIFTEMPLOYEE SET EMPLOYEENAME = ?, BRANCH = ?, BANKACCOUNT = ?, SALARY = ?, " +
                "STARTDATE = ?, RESIGNATIONDATE = ?, VACATIONDAYS = ?, ISLOGGEDIN = ?, PASSWORD = ?, " +
                "ISFULLTIME = ?, HRID = ?, LICENSE = ? WHERE EMPLOYEEID = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, shiftEmployee.getEmployeeName());
                statement.setString(2, shiftEmployee.getBranch());
                statement.setString(3, shiftEmployee.getBankAccount());
                statement.setInt(4, shiftEmployee.getSalary());
                statement.setDate(5, java.sql.Date.valueOf(shiftEmployee.getStartDate()));
                statement.setDate(6, shiftEmployee.getResignationDate() != null ? java.sql.Date.valueOf(shiftEmployee.getResignationDate()) : null);
                statement.setInt(7, shiftEmployee.getVacationDays());
                statement.setBoolean(8, shiftEmployee.isLoggedIn());
                statement.setString(9, shiftEmployee.getPassword());
                statement.setBoolean(10, shiftEmployee.isFullTime());
                statement.setInt(11, shiftEmployee.getHRid());
                statement.setString(12, shiftEmployee.getLicense().toString());
                statement.setInt(13, shiftEmployee.getID());

                statement.addBatch();
                statement.executeBatch();
                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction in case of error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ShiftEmployee shiftEmployee) {
        String query = "DELETE FROM SHIFTEMPLOYEE WHERE EMPLOYEEID = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, shiftEmployee.getID());
                statement.addBatch();
                statement.executeBatch();
                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction in case of error
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
