package DataLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import BuisnessLayer.Preferences;

public class PreferencesDao implements Dao<Preferences>{
    private Connection connection;
    @Override
    public List<Preferences> getAll() {
        return null;
    }

    @Override
    public void create(Preferences preferences) {
        String query = "INSERT INTO Preferences (employeeID, MadeAtWeek, bool11, bool12, bool21, bool22, bool31, bool32, bool41, bool42, bool51, bool52, bool61, bool62) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, preferences.getId());
            statement.setInt(2, preferences.getMadeAtWeek());
            boolean[][] shifts = preferences.getShifts();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++){
                    statement.setBoolean((i * 2) + j + 3, shifts[i][j]);
                }
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Preferences preferences) {
        String query = "UPDATE Preferences SET bool11 = ?, bool12 = ?, bool21 = ?, bool22 = ?, bool31 = ?, bool32 = ?, bool41 = ?, bool42 = ?, bool51 = ?, bool52 = ?, bool61 = ?, bool62 = ? WHERE employeeID = ? AND MadeAtWeek = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            boolean[][] shifts = preferences.getShifts();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 2; j++){
                    statement.setBoolean((i * 2) + j + 1, shifts[i][j]);
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
