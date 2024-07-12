package DataLayer.EM;

import DataLayer.DBConnection;
import DataLayer.DBConnectionEM;
import DomainLayer.EM.Shift;
import Library.Pair;
import DataLayer.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ShiftRolesDao implements Dao<Pair<Integer, Shift>> {
    private Connection connection;

    public ShiftRolesDao() {
        this.connection = DBConnectionEM.getConnection();
    }

    @Override
    public List<Pair<Integer, Shift>> getAll() {

        return new LinkedList<>();
    }

    @Override
    public void create(Pair<Integer, Shift> integerRole) {
        String query = "INSERT INTO SHIFTROLES (BRANCH,DATE,PERIOD,EMPLOYEEID,ROLE) VALUES ( ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, integerRole.getSecond().getShiftManager().getBranch());
                statement.setDate(2, java.sql.Date.valueOf(integerRole.getSecond().getDate()));
                statement.setString(3, integerRole.getSecond().getPeriod().toString());
                statement.setInt(4, integerRole.getFirst());
                statement.setString(5, integerRole.getSecond().getRole(integerRole.getFirst()));
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
    public void update(Pair<Integer, Shift> integerShiftPair) {
        String query = "UPDATE SHIFTROLES SET BRANCH = ?, DATE = ?, PERIOD = ?, ROLE = ? WHERE EMPLOYEEID = ? AND DATE = ? AND PERIOD = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, integerShiftPair.getSecond().getShiftManager().getBranch());
                statement.setDate(2, java.sql.Date.valueOf(integerShiftPair.getSecond().getDate()));
                statement.setString(3, integerShiftPair.getSecond().getPeriod().toString());
                statement.setString(4, integerShiftPair.getSecond().getRole(integerShiftPair.getFirst()));
                statement.setInt(5, integerShiftPair.getFirst());
                statement.setDate(6, java.sql.Date.valueOf(integerShiftPair.getSecond().getDate()));
                statement.setString(7, integerShiftPair.getSecond().getPeriod().toString());

                statement.executeUpdate();
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
    public void delete(Pair<Integer, Shift> integerShiftPair) {
        String query = "DELETE FROM SHIFTROLES " +
                "WHERE EMPLOYEEID = ? AND DATE = ? AND PERIOD = ? AND BRANCH = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(4, integerShiftPair.getSecond().getShiftManager().getBranch());
                statement.setDate(2, java.sql.Date.valueOf(integerShiftPair.getSecond().getDate()));
                statement.setString(3, integerShiftPair.getSecond().getPeriod().toString());
                statement.setInt(1, integerShiftPair.getFirst());
                statement.executeUpdate();
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
