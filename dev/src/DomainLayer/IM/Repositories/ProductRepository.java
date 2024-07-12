package DomainLayer.IM.Repositories;

import DataLayer.Dao;
import DataLayer.IM.ProductDao;
import DomainLayer.IM.Product;
import DomainLayer.Repository;

import java.util.List;

public class ProductRepository implements Repository<Product> {
    private Dao<Product> productDao;
    private static ProductRepository instance;

    public ProductRepository() {
        this.productDao = (Dao<Product>) new ProductDao();
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
    public void delete(Product product) {
        try {
            productDao.delete(product);
        } catch (Exception e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }
}
