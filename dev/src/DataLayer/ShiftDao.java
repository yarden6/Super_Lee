package DataLayer;

import BuisnessLayer.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftDao implements Dao<Shift>{

    private Connection connection;
    @Override
    public List<Shift> getAll() {
        List<Shift> shifts = new ArrayList<>();
        String query = "SELECT * FROM Shift";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String branch = resultSet.getString("branch");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                Employee shiftManager = getEmployeeById(resultSet.getInt("shiftManagerId"));
                LocalTime startTime = resultSet.getTime("startTime").toLocalTime();
                LocalTime endTime = resultSet.getTime("endTime").toLocalTime();
                Period period = Period.valueOf(resultSet.getString("period"));
                Map<Integer,Role> shiftRoles = getShiftRolesByBranchDatePeriod(branch, date, period);
                Shift shift = new Shift(date, shiftManager, shiftRoles, startTime, endTime, period);
                shifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return shifts;
    }

    public Map<Integer, Role> getShiftRolesByBranchDatePeriod(String branch, LocalDate date, Period period) {
        Map<Integer, Role> shiftRoles = new HashMap<>();
        String query = "SELECT s.shiftId, r.roleName " +
                "FROM Shift s " +
                "JOIN ShiftRoles sr ON s.shiftId = sr.shiftId " +
                "JOIN Role r ON sr.roleId = r.roleId " +
                "WHERE s.branch = ? AND s.date = ? AND s.period = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, branch);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, period.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int shiftId = resultSet.getInt("shiftId");
                String roleName = resultSet.getString("roleName");
                Role role = Role.valueOf(roleName); // Assuming Role has a constructor that takes a roleName
                shiftRoles.put(shiftId, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shiftRoles;
    }

    @Override
    public void create(Shift shift) {
        String query = "INSERT INTO Shift (branch, date, shiftManagerID, startTime, endTime, period) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shift.getShiftManager().getBranch());
            statement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
            statement.setInt(3, shift.getShiftManager().getID());
            statement.setTime(4, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(5, java.sql.Time.valueOf(shift.getEndTime()));
            statement.setString(6, shift.getPeriod().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Shift shift) {
        String query = "UPDATE Shift SET branch = ?, date = ?, shiftManagerID = ?, startTime = ?, endTime = ?, period = ? WHERE shiftId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, shift.getShiftManager().getBranch());
            statement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
            statement.setInt(3, shift.getShiftManager().getID());
            statement.setTime(4, java.sql.Time.valueOf(shift.getStartTime()));
            statement.setTime(5, java.sql.Time.valueOf(shift.getEndTime()));
            statement.setString(6, shift.getPeriod().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void delete(Shift shift) {}
}
