package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();
}
