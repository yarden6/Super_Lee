package domain.Repositories;

import DataLayer.Dao;
import DataLayer.ItemDao;
import DataLayer.ProductDao;
import domain.Item;
import domain.Product;

import java.util.List;

public class ProductRepository implements Repository<Product>{
    private Dao<Product> productDao;

    public ProductRepository() {
        this.productDao = new ProductDao();
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
