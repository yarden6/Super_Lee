package BuisnessLayer.Repositories;

import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import DataLayer.Dao;
import DataLayer.ShiftEmployeeRolesDao;
import Library.Pair;

import java.util.List;
import java.util.Map;

public class ShiftEmployeeRolesRepository implements Repository<Pair<Integer,Role>>{
    private static ShiftEmployeeRolesRepository instance;
    private Dao<Pair<Integer,Role>> shiftEmployeeRolesDao;

    public ShiftEmployeeRolesRepository() {
        this.shiftEmployeeRolesDao = new ShiftEmployeeRolesDao();
    }

    public static synchronized ShiftEmployeeRolesRepository getInstance() {
        if (instance == null) {
            instance = new ShiftEmployeeRolesRepository();
        }
        return instance;
    }

    @Override
    public void add(Pair<Integer,Role>employeeRoles) {
        shiftEmployeeRolesDao.create(employeeRoles);
    }

    @Override
    public void update(Pair<Integer,Role> employeeRoles) {
        shiftEmployeeRolesDao.update(employeeRoles);
    }

    @Override
    public List<Pair<Integer,Role>> findAll() {
        return shiftEmployeeRolesDao.getAll();
    }

    @Override
    public void delete(Pair<Integer,Role> employeeRoles) {
        shiftEmployeeRolesDao.delete(employeeRoles);
    }

}
