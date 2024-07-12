package DataLayer.EM;

import DataLayer.DBConnectionEM;
import DataLayer.Dao;
import DomainLayer.EM.Delivery;
import DomainLayer.EM.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDao implements Dao<Delivery> {
    private Connection connection;

    public DeliveryDao() {
        this.connection = DBConnectionEM.getConnection();
    }

    @Override
    public List<Delivery> getAll() {
        List<Delivery> deliveries = new ArrayList<>();
        String query = "SELECT * FROM DELIVERY";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Delivery delivery = new Delivery();
                delivery.setDate(resultSet.getDate("DATE").toLocalDate());
                delivery.setHour(stringToLocalTime(resultSet.getString("HOUR")));
                Vehicle vehicle = Vehicle.valueOf(resultSet.getString("VEHICLETYPE"));
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
        String query = "INSERT INTO Delivery (DATE, HOUR, VEHICLETYPE) VALUES ( ?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Start transaction
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setDate(1, java.sql.Date.valueOf(delivery.getDate()));
                statement.setString(2, localTimeToString(delivery.getHour()));
                statement.setString(3, delivery.getVehicle().toString());
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
    public void update(Delivery delivery) {
    }

    @Override
    public void delete(Delivery delivery) {
    }

    private static final String TIME_PATTERN = "HH:mm:ss";

    public static String localTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return time.format(formatter);
    }

    public static LocalTime stringToLocalTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return LocalTime.parse(timeString, formatter);
    }
}
