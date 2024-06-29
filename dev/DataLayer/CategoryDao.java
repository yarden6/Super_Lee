package DataLayer;
import DataLayer.DBConnection;
import domain.Category;
import domain.Item;

import java.sql.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements Dao<Category> {

    private Connection connection;

    public CategoryDao() {
        this.connection = DBConnection.getConnection();
    }


    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Category";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {

                String name = (resultSet.getString("name"));
                String parentName = (resultSet.getString("parentCategory"));
                int discountPercentage = (resultSet.getInt("discountPercentage"));
                String discountDate = (resultSet.getString("discountDate"));

                Category category = new Category(name, parentName, discountPercentage, changeToLocalDate(discountDate));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    private LocalDate changeToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            if (date != null) {
                LocalDate dateAsLocal = LocalDate.parse(date, formatter);
                return dateAsLocal;
            } else return null;

        } catch (DateTimeException e) {
            return null;
        }
    }

    @Override
    public void create(Category category) {
        String query = "INSERT INTO Category (name, parentCategory, discountPercentage, discountDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getName());
            if (category.getParentCategory() == null) preparedStatement.setString(2, null);
            else preparedStatement.setString(2, category.getParentCategory().getName());
            preparedStatement.setInt(3, category.getDiscountPercentage());
            preparedStatement.setString(4, category.getDiscountDateString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Category category) {
        String query = "UPDATE Category SET discountPercentage = ?, discountDate = ? WHERE name = ? AND parentCategory = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, category.getDiscountPercentage());
            preparedStatement.setString(2, category.getDiscountDateString());
            preparedStatement.setString(3, category.getName());
            if (category.getParentCategory() == null) preparedStatement.setString(4, null);
            else preparedStatement.setString(4, category.getParentCategory().getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void delete(Category category) {
        String query = "DELETE FROM Category WHERE name = ? AND (parentCategory = ? OR (parentCategory IS NULL AND ? IS NULL))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getName());
            if (category.getParentCategory() == null) {
                preparedStatement.setString(2, null);
                preparedStatement.setString(3, null);
            } else {
                preparedStatement.setString(2, category.getParentCategory().getName());
                preparedStatement.setString(3, category.getParentCategory().getName());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
