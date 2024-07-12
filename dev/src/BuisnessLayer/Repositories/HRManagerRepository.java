package BuisnessLayer.Repositories;

import BuisnessLayer.HRManager;
import DataLayer.HRManagerDao;
import DataLayer.Dao;

import java.util.List;

public class HRManagerRepository implements Repository<HRManager> {
    private static  HRManagerRepository instance;
    private Dao<HRManager> hrManagerDao;

    public HRManagerRepository() {
        this.hrManagerDao = new HRManagerDao();
    }

    public static synchronized HRManagerRepository getInstance() {
        if (instance == null) {
            instance = new HRManagerRepository();
        }
        return instance;
    }

    @Override
    public void add(HRManager hrManager) {
        hrManagerDao.create(hrManager);
    }

    @Override
    public void update(HRManager hrManager) {
        hrManagerDao.update(hrManager);
    }

    @Override
    public List<HRManager> findAll() {
        return hrManagerDao.getAll();
    }

    @Override
    public void delete(HRManager hrManager) {
        hrManagerDao.delete(hrManager);
    }

}
