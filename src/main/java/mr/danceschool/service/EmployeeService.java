package mr.danceschool.service;

import mr.danceschool.entity.Employee;
import mr.danceschool.entity.EmployeeInstructor;

import java.util.List;

public interface EmployeeService {

    public Employee save(Employee employee);

    public Employee findById(long id);

    public List<Employee> findAll();

    public List<EmployeeInstructor> findAllInstructors();

    public Integer deleteById(long id);
}
