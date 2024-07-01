package DataLayer;

import BuisnessLayer.Role;
import BuisnessLayer.Shift;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ShiftRolesDao implements Dao<Shift> {
    private Connection connection;
    @Override
    public List<Shift> getAll() {
        return List.of();
    }

    @Override
    public void create(Shift integerRoleMap) {

    }

    @Override
    public void update(Shift integerRoleMap) {

    }

    @Override
    public void delete(Shift integerRoleMap) {

    }
}
