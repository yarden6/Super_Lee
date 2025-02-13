package DomainLayer.EM.Repositories;

import DataLayer.Dao;
import DomainLayer.EM.Preferences;
import DomainLayer.EM.Role;
import DataLayer.EM.ShiftEmployeeDao;
import DomainLayer.EM.ShiftEmployee;
import DomainLayer.Repository;
import Library.Pair;

import java.util.List;

public class ShiftEmployeeRepository implements Repository<ShiftEmployee> {
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
        for (Role role : shiftEmployee.getRoles()) {
            ShiftEmployeeRolesRepository.getInstance().delete(new Pair<>(shiftEmployee.getID(), role));
        }
        for (Preferences preferences : shiftEmployee.getPreferences()) {
            PreferencesRepository.getInstance().delete(preferences);
        }
        shiftEmployeeDao.delete(shiftEmployee);
    }
}
