package DataLayer;

import domain.Category;
import domain.Item;
import domain.Product;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product> {
    private Connection connection;

    public ProductDao(){
        this.connection = DBConnection.getConnection();
    }
    private LocalDate changeToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate dateAsLocal = LocalDate.parse(date, formatter);
            return dateAsLocal;
        } catch (DateTimeException e) {
            return null;
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int MKT = resultSet.getInt("MKT");
                String mainCat = resultSet.getString("mainCat");
                String subCat = resultSet.getString("subCat");
                String subSubCat = resultSet.getString("subSubCat");
                String name = resultSet.getString("name");
                int aisle= resultSet.getInt("aisle");
                String producerName= resultSet.getString("producerName");
                int totalAmount= resultSet.getInt("totalAmount");
                int storeAmount= resultSet.getInt("storeAmount");
                int storageAmount= resultSet.getInt("storageAmount");
                double sellingPrice= resultSet.getDouble("sellingPrice");
                int deliveryDays= resultSet.getInt("deliveryDays");
                int minimumAmount= resultSet.getInt("minimumAmount");
                int discountPercentage= resultSet.getInt("discountPercentage");
                LocalDate discountDate= changeToLocalDate(resultSet.getString("discountDate"));
                String supplier= resultSet.getString("supplier");

                Product product = new Product(MKT,mainCat,subCat,subSubCat,name,aisle,producerName,totalAmount,storeAmount,storageAmount,sellingPrice,deliveryDays,minimumAmount,discountPercentage,discountDate,supplier);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void create(Product product) {
        String query = "INSERT INTO Product (MKT, name, aisle,producerName,totalAmount,storeAmount,storageAmount,sellingPrice,deliveryDays,minimumAmount,discountPercentage,discountDate,supplier,mainCat, subCat,subSubCat) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, product.getMKT());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getAisle());
            preparedStatement.setString(4, product.getProducerName());
            preparedStatement.setInt(5, product.getTotalAmount());
            preparedStatement.setInt(6, product.getStoreAmount());
            preparedStatement.setInt(7, product.getStorageAmount());
            preparedStatement.setDouble(8, product.getSellingPrice());
            preparedStatement.setInt(9, product.getDeliveryDays());
            preparedStatement.setInt(10, product.getMinimumAmount());
            preparedStatement.setInt(11, product.getDiscountPercentage());
            preparedStatement.setString(12, product.getDiscountDate().toString());
            preparedStatement.setString(13, product.getSupplier());
            preparedStatement.setString(14, product.getCategoryMain());
            preparedStatement.setString(15, product.getCategorySub());
            preparedStatement.setString(16, product.getCategorySubSub());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void update(Product product) {
        String query = "UPDATE Product " +
                "SET aisle = ?, " +
                "producerName = ?, " +
                "totalAmount = ?, " +
                "storeAmount = ?, " +
                "storageAmount = ?, " +
                "sellingPrice = ?, " +
                "deliveryDays = ?, " +
                "minimumAmount = ?, " +
                "discountPercentage = ?, " +
                "discountDate = ?, " +
                "supplier = ?, " +
                "WHERE name = ? AND mainCat = ? AND subCat = ? AND subSubCat = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, product.getAisle());
            preparedStatement.setString(2, product.getProducerName());
            preparedStatement.setInt(3, product.getTotalAmount());
            preparedStatement.setInt(4, product.getStoreAmount());
            preparedStatement.setInt(5, product.getStorageAmount());
            preparedStatement.setDouble(6, product.getSellingPrice());
            preparedStatement.setInt(7, product.getDeliveryDays());
            preparedStatement.setInt(8, product.getMinimumAmount());
            preparedStatement.setInt(9, product.getDiscountPercentage());
            preparedStatement.setString(10, product.getDiscountDate().toString());
            preparedStatement.setString(11, product.getSupplier());
            preparedStatement.setString(12, product.getName());
            preparedStatement.setString(13, product.getCategoryMain());
            preparedStatement.setString(14, product.getCategorySub());
            preparedStatement.setString(15, product.getCategorySubSub());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
