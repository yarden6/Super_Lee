package BuisnessLayer.Repositories;

import BuisnessLayer.Shift;
import DataLayer.Dao;
import DataLayer.ShiftDao;

import java.util.List;

public class ShiftRepository implements Repository<Shift> {
    private static  ShiftRepository instance;
    private Dao<Shift> shiftDao;

    public ShiftRepository() {
        this.shiftDao = new ShiftDao();
    }

    public static synchronized ShiftRepository getInstance() {
        if (instance == null) {
            instance = new ShiftRepository();
        }
        return instance;
    }

    @Override
    public void add(Shift shift) {
        shiftDao.create(shift);
    }

    @Override
    public void update(Shift shift) {
        shiftDao.update(shift);
    }

    @Override
    public List<Shift> findAll() {
        return shiftDao.getAll();
    }

    @Override
    public void delete(Shift shift) {
        shiftDao.delete(shift);
    }
}
