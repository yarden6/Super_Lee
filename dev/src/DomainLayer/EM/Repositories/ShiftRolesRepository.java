package DomainLayer.EM.Repositories;

import DomainLayer.EM.Shift;
import DataLayer.EM.ShiftRolesDao;
import DomainLayer.Repository;
import Library.Pair;
import java.util.List;
import DataLayer.Dao;

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
