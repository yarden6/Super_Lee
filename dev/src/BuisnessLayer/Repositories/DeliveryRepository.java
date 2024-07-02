package BuisnessLayer.Repositories;

import BuisnessLayer.Delivery;
import DataLayer.Dao;
import DataLayer.DeliveryDao;

import java.util.List;

public class DeliveryRepository implements Repository<Delivery>{
    private static DeliveryRepository instance;
    private Dao<Delivery> deliveryDao;

    public DeliveryRepository() {
        this.deliveryDao = new DeliveryDao();
    }

    public static synchronized DeliveryRepository getInstance() {
        if (instance == null) {
            instance = new DeliveryRepository();
        }
        return instance;
    }

    @Override
    public void add(Delivery delivery) {
        deliveryDao.create(delivery);
    }

    @Override
    public void update(Delivery delivery) {
        deliveryDao.update(delivery);
    }

    @Override
    public List<Delivery> findAll() {
        return deliveryDao.getAll();
    }

    @Override
    public void delete(Delivery delivery) {
        deliveryDao.delete(delivery);
    }

}
