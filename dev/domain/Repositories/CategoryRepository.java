package domain.Repositories;

import DataLayer.CategoryDao;
import DataLayer.Dao;
import DataLayer.ProductDao;
import domain.Category;
import domain.Item;
import domain.Product;

import java.util.List;

public class CategoryRepository implements Repository<Category> {
    private static CategoryRepository instance;
    private Dao<Category> categoryDao;

    public CategoryRepository() {
        this.categoryDao = new CategoryDao();
    }

    public static synchronized CategoryRepository getInstance() {
        if (instance == null) {
            instance = new CategoryRepository();
        }
        return instance;
    }

    @Override
    public void add(Category category) {
        categoryDao.create(category);
    }

    @Override
    public void update(Category category) {
        try {
            categoryDao.update(category);
        } catch (Exception e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.getAll();
    }
}
