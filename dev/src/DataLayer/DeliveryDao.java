package DataLayer;

import BuisnessLayer.Delivery;
import BuisnessLayer.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDao implements Dao<Delivery> {
    private Connection connection;

    public DeliveryDao() {
        this.connection = DBConnection.getConnection();
    }

    @Override
    public List<Delivery> getAll() {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT date, hour, vehicleId FROM Delivery";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDate(resultSet.getDate("date").toLocalDate());
                delivery.setHour(resultSet.getTime("hour").toLocalTime());
                Vehicle vehicle = Vehicle.valueOf(resultSet.getString("vehicleId"));
                delivery.setVehicle(vehicle);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveries;
    }

    @Override
    public void create(Delivery delivery) {
        String query = "INSERT INTO Delivery (date, hour, vehicleId) VALUES ( ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, java.sql.Date.valueOf(delivery.getDate()));
            statement.setTime(2, java.sql.Time.valueOf(delivery.getHour()));
            statement.setString(3, delivery.getVehicle().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Delivery delivery) {}

    @Override
    public void delete(Delivery delivery) {}
}
