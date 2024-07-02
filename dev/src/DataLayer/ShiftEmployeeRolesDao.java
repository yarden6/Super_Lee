package DataLayer;

import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ShiftEmployeeRolesDao implements Dao<Map<Integer,List<Role>>> {

    private Connection connection;

    public ShiftEmployeeRolesDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Map<Integer,List<Role>>> getAll() {
        Map<Integer, List<Role>> employeeRoles = new HashMap<>();
        String query = "SELECT employeeId, roleId FROM ShiftEmplyeeRoles";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employeeId");
                String roleId = resultSet.getString("roleId");
                Role role = Role.valueOf(roleId);
                employeeRoles.computeIfAbsent(employeeId, k -> new ArrayList<>()).add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of(employeeRoles);
    }

    @Override
    public void create(Map<Integer, List<Role>> employeeRoles) {
        String query = "INSERT INTO ShiftEmplyeeRoles (employeeId, roleId) VALUES (?, ?)";
        try {
            // Assuming the connection is already established and available as 'connection'
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Map.Entry<Integer, List<Role>> entry : employeeRoles.entrySet()) {
                    int employeeId = entry.getKey();
                    for (Role role : entry.getValue()) {
                        statement.setInt(1, employeeId);
                        statement.setString(2, role.name());
                        statement.addBatch();
                    }
                }
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
    public void update(Map<Integer, List<Role>> employeeRoles) {}


    @Override
    public void delete(Map<Integer, List<Role>> employeeRoles) {
        String query = "DELETE FROM ShiftEmplyeeRoles WHERE employeeId = ? AND roleId = ?";
        try {
            // Assuming the connection is already established and available as 'connection'
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (Map.Entry<Integer, List<Role>> entry : employeeRoles.entrySet()) {
                    int employeeId = entry.getKey();
                    for (Role role : entry.getValue()) {
                        statement.setInt(1, employeeId);
                        statement.setString(2, role.name()); // Assuming 'Role' is an enum
                        statement.addBatch();
                    }
                }
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
