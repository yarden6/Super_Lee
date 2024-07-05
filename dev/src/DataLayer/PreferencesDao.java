package DataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import BuisnessLayer.Preferences;

public class PreferencesDao implements Dao<Preferences>{
    private Connection connection;

    public PreferencesDao() {
        this.connection = DBConnection.getConnection();
    }
    @Override
    public List<Preferences> getAll() {
        return null;
    }

    @Override
    public void create(Preferences preferences) {
        String query = "INSERT INTO PREFERENCES (EMPLOYEEID, MADEATWEEK, BOOL11, BOOL12, BOOL21, BOOL22, BOOL31, BOOL32," +
                " BOOL41, BOOL42, BOOL51, BOOL52, BOOL61, BOOL62) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Preferences preferences) {
        String query = "UPDATE PREFERENCES SET BOOL11 = ?, BOOL12 = ?, BOOL21 = ?, BOOL22 = ?, BOOL31 = ?, BOOL32 = ?, " +
                "BOOL41 = ?, BOOL42 = ?, BOOL51 = ?, BOOL52 = ?, BOOL61 = ?, BOOL62 = ? WHERE EMPLOYEEID = ? AND MADEATWEEK = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            boolean[][] shifts = preferences.getShifts();
            int index = 3;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++) {
                    statement.setBoolean(index ,shifts[i][j]);
                    index++;
                }
            }
            statement.setInt(13, preferences.getId());
            statement.setInt(14, preferences.getMadeAtWeek());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                System.out.println("No record found with employeeID " + preferences.getId() + " and MadeAtWeek " + preferences.getMadeAtWeek());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Preferences preferences) {

    }
}
