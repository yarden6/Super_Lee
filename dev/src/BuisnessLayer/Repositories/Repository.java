package BuisnessLayer.Repositories;

import java.sql.SQLException;
import java.util.List;

public interface Repository <T> {
    public void add (T t);
    public void update(T t);
    public List<T> findAll() throws SQLException;
    public void delete(T t);
}
