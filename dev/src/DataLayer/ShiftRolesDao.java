package DataLayer;

import BuisnessLayer.Period;
import BuisnessLayer.Role;
import BuisnessLayer.Shift;
import Library.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShiftRolesDao implements Dao<Pair<Integer, Shift>> {
    private Connection connection;

    public ShiftRolesDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Pair<Integer, Shift>> getAll() {

        return new LinkedList<>();
    }

    @Override
    public void create(Pair<Integer, Shift> integerRole) {
        String query = "INSERT INTO SHIFTROLES (BRANCH,DATE,PERIOD,EMPLOYEEID,ROLE) VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, integerRole.getSecond().getShiftManager().getBranch());
            statement.setDate(2, java.sql.Date.valueOf(integerRole.getSecond().getDate()));
            statement.setString(3, integerRole.getSecond().getPeriod().toString());
            statement.setInt(4, integerRole.getFirst());
            statement.setString(5, integerRole.getSecond().getRole(integerRole.getFirst()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pair<Integer,Shift> integerRoleMap) {

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
