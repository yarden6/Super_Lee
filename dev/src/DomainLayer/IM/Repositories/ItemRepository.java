package DomainLayer.IM.Repositories;

import DataLayer.IM.ItemDao;
import DomainLayer.IM.Item;
import DomainLayer.Repository;

import java.util.List;

public class ItemRepository implements Repository<Item> {
    private ItemDao itemDao;
    private static ItemRepository instance;

    public ItemRepository() {
        this.itemDao = new ItemDao();
    }

    public static synchronized ItemRepository getInstance() {
        if (instance == null) {
            instance = new ItemRepository();
        }
        return instance;
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

    public void delete(Item item) {
        try {
            itemDao.delete(item);
        } catch (Exception e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }
}
