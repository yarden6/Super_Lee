package DataLayer;

import domain.Category;
import domain.Item;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ItemDao implements Dao<Item> {

    private Connection connection;

    public ItemDao(){
        this.connection = DBConnection.getConnection();
    }

    private LocalDate changeToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if (date != null) {
                LocalDate dateAsLocal = LocalDate.parse(date, formatter);
                return dateAsLocal;
            }
            return null;
        } catch (DateTimeException e) {
            return null;
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int itemId =(resultSet.getInt("itemId"));;
                int productMKT =(resultSet.getInt("productMKT"));;
                String expirationDate =(resultSet.getString("expirationDate"));;
                double buyingPrice =(resultSet.getDouble("buyingPrice"));;
                int buyingDiscount=(resultSet.getInt("buyingDiscount"));;
                String location =(resultSet.getString("location")); ;

                Item item = new Item(itemId,productMKT,changeToLocalDate(expirationDate),buyingPrice,buyingDiscount,location);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public void create(Item item) {
        String query = "INSERT INTO Item (itemId, productMKT, expirationDate, buyingPrice, buyingDiscount,location) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, item.getItemId());
            preparedStatement.setInt(2, item.getProductMKT());
            preparedStatement.setString(3, item.getExpirationDate().toString());
            preparedStatement.setDouble(4, item.getBuyingPrice());
            preparedStatement.setDouble(5, item.getBuyingDiscount());
            preparedStatement.setString(6, item.getLocationName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Item item) {
        String query = "UPDATE Item SET location = ? WHERE itemId = ? AND productMKT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getLocationName());
            preparedStatement.setInt(2, item.getItemId());
            preparedStatement.setInt(3, item.getProductMKT());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
