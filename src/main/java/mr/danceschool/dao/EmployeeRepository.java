package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAll();
}
