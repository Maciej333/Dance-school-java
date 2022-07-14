package s18454.diploma.service;

import s18454.diploma.entity.Employee;
import s18454.diploma.entity.EmployeeInstructor;

import java.util.List;

public interface EmployeeService {

    public Employee save(Employee employee);

    public Employee findById(long id);

    public List<Employee> findAll();

    public List<EmployeeInstructor> findAllInstructors();

    public Integer deleteById(long id);
}
