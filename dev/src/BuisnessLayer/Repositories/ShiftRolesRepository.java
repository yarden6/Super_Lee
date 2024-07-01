package BuisnessLayer.Repositories;

import BuisnessLayer.Role;
import BuisnessLayer.Shift;
import DataLayer.Dao;
import DataLayer.ShiftRolesDao;

import java.util.List;
import java.util.Map;

public class ShiftRolesRepository implements Repository<Shift> {
    private static ShiftRolesRepository instance;
    private Dao<Shift> shiftRolesDao;

    public ShiftRolesRepository() {
        this.shiftRolesDao = new ShiftRolesDao();
    }

    public static synchronized ShiftRolesRepository getInstance() {
        if (instance == null) {
            instance = new ShiftRolesRepository();
        }
        return instance;
    }

    @Override
    public void add(Shift shiftRoles) {
        shiftRolesDao.create(shiftRoles);
    }

    @Override

    public void update(Shift shiftRoles) {
        shiftRolesDao.update(shiftRoles);
    }

    @Override

    public List<Shift> findAll() {
        return shiftRolesDao.getAll();
    }

    @Override

    public void delete(Shift shiftRoles) {
        shiftRolesDao.delete(shiftRoles);
    }


}
