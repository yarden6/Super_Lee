package DataLayer;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface Dao <T>{
    List<T> getAll() ;

    void create(T t);

    void update(T t);

    void delete (T t);


}
