package BuisnessLayer.Repositories;

import BuisnessLayer.Role;
import BuisnessLayer.Shift;
import DataLayer.Dao;
import DataLayer.ShiftRolesDao;
import Library.Pair;

import java.util.List;
import java.util.Map;

public class ShiftRolesRepository implements Repository<Pair<Integer,Shift>> {
    private static ShiftRolesRepository instance;
    private Dao<Pair<Integer,Shift>> shiftRolesDao;

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
    public void add(Pair<Integer,Shift> shiftRoles) {
        shiftRolesDao.create(shiftRoles);
    }

    @Override

    public void update(Pair<Integer,Shift> shiftRoles) {
        shiftRolesDao.update(shiftRoles);
    }

    @Override

    public List<Pair<Integer,Shift>> findAll() {
        return shiftRolesDao.getAll();
    }

    @Override

    public void delete(Pair<Integer,Shift> shiftRoles) {
        shiftRolesDao.delete(shiftRoles);
    }


}
