package BuisnessLayer.Repositories;

import BuisnessLayer.Role;
import BuisnessLayer.ShiftEmployee;
import DataLayer.Dao;
import DataLayer.ShiftEmployeeRolesDao;

import java.util.List;
import java.util.Map;

public class ShiftEmployeeRolesRepository implements Repository<Map<Integer,List<Role>>>{
    private static ShiftEmployeeRolesRepository instance;
    private Dao<Map<Integer,List<Role>>> shiftEmployeeRolesDao;

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
    public void add(Map<Integer,List<Role>> employeeRoles) {
        shiftEmployeeRolesDao.create(employeeRoles);
    }

    @Override
    public void update(Map<Integer,List<Role>> employeeRoles) {
        shiftEmployeeRolesDao.update(employeeRoles);
    }

    @Override
    public List<Map<Integer,List<Role>>> findAll() {
        return shiftEmployeeRolesDao.getAll();
    }

    @Override
    public void delete(Map<Integer,List<Role>> employeeRoles) {
        shiftEmployeeRolesDao.delete(employeeRoles);
    }

}
