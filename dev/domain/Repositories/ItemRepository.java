package domain.Repositories;

import DataLayer.Dao;
import DataLayer.ItemDao;
import domain.Category;
import domain.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemRepository implements Repository<Item>{
    private Dao<Item> itemDao;
    public ItemRepository() {
        this.itemDao = new ItemDao();
    }

    @Override
    public void add(Item item) {
        itemDao.create(item);
    }

    @Override
    public void update(Item item) {
        try {
            itemDao.update(item);
        } catch (Exception e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    @Override
    public List<Item> findAll() {
        return itemDao.getAll();
    }
}
