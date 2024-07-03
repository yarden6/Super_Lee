package BuisnessLayer.Repositories;

import DataLayer.ShiftEmployeeDao;
import BuisnessLayer.ShiftEmployee;
import DataLayer.Dao;
import java.util.List;

public class ShiftEmployeeRepository implements Repository<ShiftEmployee>{
    private static  ShiftEmployeeRepository instance;
    private Dao<ShiftEmployee> shiftEmployeeDao;

    public ShiftEmployeeRepository() {
        this.shiftEmployeeDao = new ShiftEmployeeDao();
    }

    public static synchronized ShiftEmployeeRepository getInstance() {
        if (instance == null) {
            instance = new ShiftEmployeeRepository();
        }
        return instance;
    }

    @Override
    public void add(ShiftEmployee shiftEmployee) {
        shiftEmployeeDao.create(shiftEmployee);
    }

    @Override
    public void update(ShiftEmployee shiftEmployee) {
        shiftEmployeeDao.update(shiftEmployee);
    }

    @Override
    public List<ShiftEmployee> findAll() {
        return shiftEmployeeDao.getAll();
    }

    @Override
    public void delete(ShiftEmployee shiftEmployee) {
        shiftEmployeeDao.delete(shiftEmployee);
    }
}
