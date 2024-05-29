package BuisnessLayer;

import java.util.List;
import java.util.Map;

public class EmployeeFacade {
    Map<Integer,Employee> employees;

    public Map<Integer,Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Map<Integer,Employee> employees) {
        this.employees = employees;
    }
}
