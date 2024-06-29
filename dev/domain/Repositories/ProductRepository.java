package domain.Repositories;

import DataLayer.Dao;
import DataLayer.ItemDao;
import DataLayer.ProductDao;
import domain.Item;
import domain.Product;

import java.util.List;

public class ProductRepository implements Repository<Product>{
    private Dao<Product> productDao;
    private static ProductRepository instance;

    public ProductRepository() {
        this.productDao = new ProductDao();
    }

    public static synchronized ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }
    @Override
    public void add(Product product) {
        productDao.create(product);
    }

    @Override
    public void update(Product product) {
        try {
            productDao.update(product);
        } catch (Exception e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    @Override
    public List<Product> findAll() {
        return productDao.getAll();
    }
}
