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

    public ShiftDao() {
        this.connection = DBConnection.getConnection();
    }
    @Override
    public List<Shift> getAll() {
        List<Shift> shifts = new ArrayList<>();
        String query = "SELECT * FROM SHIFT";
        try (PreparedStatement statement = connection.prepareStatement(query.toUpperCase())) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String branch = resultSet.getString("BRANCH");
                LocalDate date = resultSet.getDate("DATE").toLocalDate();
                Employee shiftManager = getEmployeeById(resultSet.getInt("SHIFTMANAGERID"));
                LocalTime startTime = resultSet.getTime("STARTTIME").toLocalTime();
                LocalTime endTime = resultSet.getTime("ENDTIME").toLocalTime();
                Period period = Period.valueOf(resultSet.getString("PERIOD"));
                Map<Integer,Role> shiftRoles = getShiftRolesByBranchDatePeriod(branch, date, period);
                Shift shift = new Shift(date, shiftManager, shiftRoles, startTime, endTime, period);
                shifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    private Employee getEmployeeById(int shiftManagerId) {
        //TODO - implement this method to get ShiftManager by ID
        return null;
    }

    public Map<Integer, Role> getShiftRolesByBranchDatePeriod(String branch, LocalDate date, Period period) {
        Map<Integer, Role> shiftRoles = new HashMap<>();
        String query = "SELECT EMPLOYEEID, ROLE" +
                " FROM SHIFTROLES" +
                " WHERE BRANCH = ? AND DATE = ? AND PERIOD = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, branch);
            statement.setDate(2, java.sql.Date.valueOf(date));
            statement.setString(3, period.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int shiftId = resultSet.getInt("EMPLOYEEID");
                String roleName = resultSet.getString("ROLE");
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
        String query = "INSERT INTO SHIFT (BRANCH, DATE, SHIFTMANAGERID, STARTTIME, ENDTIME, PERIOD) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query.toUpperCase())) {
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
        String query = "UPDATE SHIFT SET BRANCH = ?, DATE = ?, SHIFTMANAGERID = ?, STARTTIME = ?, ENDTIME = ?, PERIOD = ?" +
                " WHERE SHIFTID = ?";
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
    public void delete(Shift shift) {
        String query = "DELETE FROM SHIFT WHERE BRANCH = ? AND DATE = ? AND PERIOD = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String branch = shift.getShiftManager().getBranch();
            preparedStatement.setString(1,branch );
            preparedStatement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
            preparedStatement.setString(1,shift.getPeriod().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        query = "DELETE FROM SHIFTROLES WHERE BRANCH = ? AND DATE = ? AND PERIOD = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String branch = shift.getShiftManager().getBranch();
            preparedStatement.setString(1,branch );
            preparedStatement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
            preparedStatement.setString(1,shift.getPeriod().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
