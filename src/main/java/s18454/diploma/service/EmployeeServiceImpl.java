package s18454.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s18454.diploma.dao.EmployeeInstructorRepository;
import s18454.diploma.dao.EmployeeRepository;
import s18454.diploma.entity.Employee;
import s18454.diploma.entity.EmployeeInstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeInstructorRepository employeeInstructorRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeInstructorRepository employeeInstructorRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeInstructorRepository = employeeInstructorRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            return employee.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll().stream().sorted(Comparator.comparing((Employee emp) -> emp.getUser().getLastname().toLowerCase())).toList();
    }

    @Override
    public List<EmployeeInstructor> findAllInstructors() {
        return employeeInstructorRepository.findAll().stream().sorted(Comparator.comparing((Employee emp) -> emp.getUser().getLastname().toLowerCase())).toList();
    }

    @Override
    public Integer deleteById(long id) {
        employeeRepository.deleteById(id);
        return 1;
    }
}
