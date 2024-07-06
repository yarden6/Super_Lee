package DataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BuisnessLayer.Preferences;

public class PreferencesDao implements Dao<Preferences>{
    private Connection connection;

    public PreferencesDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Preferences> getAll() {
        List<Preferences> preferencesList = new ArrayList<>();
        String query = "SELECT * FROM PREFERENCES";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("EMPLOYEEID");
                int madeAtWeek = resultSet.getInt("MADEATWEEK");
                boolean[][] shifts = new boolean[6][2];
                int index = 1;
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 2; j++) {
                        shifts[i][j] = resultSet.getBoolean("BOOL" + (i+1) + (j+1));
                    }
                }
                Preferences preferences = new Preferences(shifts, madeAtWeek, id);
                preferencesList.add(preferences);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preferencesList;
    }
    @Override
    public void create(Preferences preferences) {
        String query = "INSERT INTO PREFERENCES (EMPLOYEEID, MADEATWEEK, BOOL11, BOOL12, BOOL21, BOOL22, BOOL31, BOOL32," +
                " BOOL41, BOOL42, BOOL51, BOOL52, BOOL61, BOOL62)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Start transaction
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, preferences.getId());
            statement.setInt(2, preferences.getMadeAtWeek());
            boolean[][] shifts = preferences.getShifts();
            int index = 3;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++) {
                    statement.setBoolean(index ,shifts[i][j]);
                    index++;
                }
            }
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
    public void update(Preferences preferences) {
        String query = "UPDATE PREFERENCES SET BOOL11 = ?, BOOL12 = ?, BOOL21 = ?, BOOL22 = ?, BOOL31 = ?, BOOL32 = ?, " +
                "BOOL41 = ?, BOOL42 = ?, BOOL51 = ?, BOOL52 = ?, BOOL61 = ?, BOOL62 = ? WHERE EMPLOYEEID = ? AND MADEATWEEK = ?;";
        try {
            connection.setAutoCommit(false); // Start transaction
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            boolean[][] shifts = preferences.getShifts();
            int index = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++) {
                    statement.setBoolean(index ,shifts[i][j]);
                    index++;
                }
            }
            statement.setInt(13, preferences.getId());
            statement.setInt(14, preferences.getMadeAtWeek());

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
    public void delete(Preferences preferences) {

    }
}
