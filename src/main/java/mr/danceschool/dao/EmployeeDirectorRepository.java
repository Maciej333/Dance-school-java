package s18454.diploma.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import s18454.diploma.entity.EmployeeDirector;

import java.util.List;

public interface EmployeeDirectorRepository extends JpaRepository<EmployeeDirector, Long> {

    List<EmployeeDirector> findAll();
}
