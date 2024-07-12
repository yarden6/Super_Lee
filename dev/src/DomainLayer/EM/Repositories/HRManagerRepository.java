package DomainLayer.EM.Repositories;

import DataLayer.Dao;
import DomainLayer.EM.HRManager;
import DataLayer.EM.HRManagerDao;
import DomainLayer.Repository;

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
