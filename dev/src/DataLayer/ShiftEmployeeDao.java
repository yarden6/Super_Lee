package DataLayer;

import BuisnessLayer.Preferences;
import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import BuisnessLayer.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ShiftEmployeeDao implements  Dao<ShiftEmployee>{
    private Connection connection;

    @Override
    public List<ShiftEmployee> getAll() {
        List<ShiftEmployee> shiftEmployees = new ArrayList<>();
        String query = "SELECT e.employeeID, e.employeeName, e.branch, e.bankAccount, e.salary, " +
                "e.password, s.isFullTime, s.HRid, s.License " +
                "FROM ShiftEmployee s " +
                "JOIN Employee e ON s.employeeID = e.employeeID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String employeeName = resultSet.getString("employeeName");
                int employeeId = resultSet.getInt("employeeID");
                String branch = resultSet.getString("branch");
                String bankAccount = resultSet.getString("bankAccount");
                int salary = resultSet.getInt("salary");
                String password = resultSet.getString("password");
                boolean isFullTime = resultSet.getBoolean("isFullTime");
                int HRid = resultSet.getInt("HRid");
                String license = resultSet.getString("License");

                ShiftEmployee shiftEmployee = new ShiftEmployee(employeeName, employeeId, branch, bankAccount, isFullTime, salary, password, HRid, null, Vehicle.valueOf(license));
                List<Role> roles = getRolesByEmployeeId(employeeId);
                for (Role role : roles) {
                    shiftEmployee.addRole(role);
                }
                shiftEmployees.add(shiftEmployee);

                List<Preferences> preferences = getPreferencesByEmployeeId(employeeId);
                for (Preferences preference : preferences) {
                    shiftEmployee.callPreferences(preference.getShifts(), preference.getMadeAtWeek());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shiftEmployees;
    }

    private List<Role> getRolesByEmployeeId(int employeeId) {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT r.roleName " +
                "FROM ShiftEmployeeRole ser " +
                "JOIN Role r ON ser.roleID = r.roleID " +
                "WHERE ser.employeeID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String roleName = resultSet.getString("roleName");
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
        String query = "SELECT * FROM Preferences WHERE employeeID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int madeAtWeek = resultSet.getInt("MadeAtWeek");
                boolean[][] shifts = new boolean[6][2];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 2; j++) {
                        String columnName = "bool" + (i +1 ) + (j + 1);
                        shifts[i][j] = resultSet.getBoolean(columnName);
                    }
                }

                Preferences preference = new Preferences(shifts, madeAtWeek,employeeId);
                preferences.add(preference);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preferences;
    }

    @Override
    public void create(ShiftEmployee shiftEmployee) {
        String query = "INSERT INTO ShiftEmployee (employeeID, employeeName, branch, bankAccount, salary, password, isFullTime, HRid, License) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shiftEmployee.getID());
            statement.setString(2, shiftEmployee.getEmployeeName());
            statement.setString(3, shiftEmployee.getBranch());
            statement.setString(4, shiftEmployee.getBanckAccount());
            statement.setInt(5, shiftEmployee.getSalary());
            statement.setString(6, shiftEmployee.getPassword());
            statement.setBoolean(7, shiftEmployee.isFullTime());
            statement.setInt(8, shiftEmployee.getHRid());
            statement.setString(9, shiftEmployee.getLicense().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ShiftEmployee shiftEmployee) {
        //TODO
    }

    @Override
    public void delete(ShiftEmployee shiftEmployee) {
        //TODO
    }
}
