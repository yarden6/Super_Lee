package DataLayer;
import DataLayer.DBConnection;
import domain.Category;

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
    public void create(Category category) {
        String query = "INSERT INTO Category (name, parentCategory, discountPrecentage, discountDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getParentCategory().getName());
            preparedStatement.setInt(3, category.getDiscountPercentage());
            preparedStatement.setString(4, category.getDiscountDate().toString());

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
            preparedStatement.setString(2, category.getDiscountDate().toString());
            preparedStatement.setString(3, category.getName());
            preparedStatement.setString(4, category.getParentCategory().getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

//    public Category read(String categoryName, String parentCategoryName) throws SQLException {
//        Category category = null;
//        try (PreparedStatement stmt = connection.prepareStatement(SELECT_CATEGORY_BY_NAME)) {
//            stmt.setString(1, categoryName);
//            stmt.setString(2, parentCategoryName);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    category = new Category();
//                    category.setCategoryName(rs.getString("category_name"));
//                    category.setParentCategoryName(rs.getString("parent_category_name"));
//                    category.setDiscount(rs.getDouble("discount"));
//                    // Set other properties as needed
//                }
//            }
//        }
//        return category;
//    }
}
