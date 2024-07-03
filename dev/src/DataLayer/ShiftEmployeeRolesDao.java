package DataLayer;

import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import Library.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class ShiftEmployeeRolesDao implements Dao<Pair<Integer,Role>> {

    private Connection connection;

    public ShiftEmployeeRolesDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Pair<Integer,Role>> getAll() {
        List<Pair<Integer,Role>> employeeRoles = new LinkedList<>() {
        };
        String query = "SELECT employeeId, roleId FROM ShiftEmplyeeRoles";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("employeeId");
                String roleId = resultSet.getString("roleId");
                Role role = Role.valueOf(roleId);
                employeeRoles.add(new Pair<>(employeeId,role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeRoles;
    }

    @Override
    public void create(Pair<Integer,Role> employeeRoles) {
        String query = "INSERT INTO ShiftEmplyeeRoles (employeeId, roleId) VALUES (?, ?)";
        try {
            // Assuming the connection is already established and available as 'connection'
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeRoles.getFirst());
                statement.setString(2, employeeRoles.getSecond().toString());//itamar changed it i think its good
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
    public void update(Pair<Integer,Role> employeeRoles) {}     //now its easier


    @Override
    public void delete(Pair<Integer,Role> employeeRoles) {
        String query = "DELETE FROM ShiftEmplyeeRoles WHERE employeeId = ? AND roleId = ?";
        try {
            // Assuming the connection is already established and available as 'connection'
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, employeeRoles.getFirst());
                statement.setString(2,employeeRoles.getSecond().toString()); // Assuming 'Role' is an enumstatement.addBatch();
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
