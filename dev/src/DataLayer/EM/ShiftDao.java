package DataLayer.EM;

import DataLayer.DBConnectionEM;
import DomainLayer.EM.Period;
import DomainLayer.EM.Role;
import DomainLayer.EM.Shift;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DataLayer.Dao;


public class ShiftDao implements Dao<Shift> {

    private Connection connection;

    public ShiftDao() {
        this.connection = DBConnectionEM.getConnection();
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
                LocalTime startTime = stringToLocalTime(resultSet.getString("STARTTIME"));
                LocalTime endTime = stringToLocalTime(resultSet.getString("ENDTIME"));
                Period period = Period.valueOf(resultSet.getString("PERIOD"));
                Map<Integer, Role> shiftRoles = getShiftRolesByBranchDatePeriod(branch, date, period);
                Shift shift = new Shift(date, null, shiftRoles, startTime, endTime, period);
                shifts.add(shift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shifts;
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
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query.toUpperCase())) {
                statement.setString(1, shift.getShiftManager().getBranch());
                statement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
                statement.setInt(3, shift.getShiftManager().getID());
                statement.setString(4, localTimeToString(shift.getStartTime()));
                statement.setString(5, localTimeToString(shift.getEndTime()));
                statement.setString(6, shift.getPeriod().toString());

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
    public void update(Shift shift) {
        String query = "UPDATE SHIFT SET BRANCH = ?, DATE = ?, SHIFTMANAGERID = ?, STARTTIME = ?, ENDTIME = ?, PERIOD = ?" +
                " WHERE SHIFTID = ?";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, shift.getShiftManager().getBranch());
                statement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
                statement.setInt(3, shift.getShiftManager().getID());
                statement.setTime(4, java.sql.Time.valueOf(shift.getStartTime()));
                statement.setTime(5, java.sql.Time.valueOf(shift.getEndTime()));
                statement.setString(6, shift.getPeriod().toString());

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


    private static final String TIME_PATTERN = "HH:mm:ss";
    public static LocalTime stringToLocalTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return LocalTime.parse(timeString, formatter);
    }

    public static String localTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return time.format(formatter);
    }

    @Override
    public void delete(Shift shift) {
        String query = "DELETE FROM SHIFTROLES " +
                "WHERE BRANCH = ? AND DATE = ? AND PERIOD = ? ";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String branch = shift.getShiftManager().getBranch();
                preparedStatement.setString(1, branch);
                preparedStatement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
                preparedStatement.setString(3, shift.getPeriod().toString());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction in case of error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        query = "DELETE FROM SHIFT" +
                " WHERE BRANCH = ? AND DATE = ? AND PERIOD = ? ";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                String branch = shift.getShiftManager().getBranch();
                preparedStatement.setString(1, branch);
                preparedStatement.setDate(2, java.sql.Date.valueOf(shift.getDate()));
                preparedStatement.setString(3, shift.getPeriod().toString());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction in case of error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
