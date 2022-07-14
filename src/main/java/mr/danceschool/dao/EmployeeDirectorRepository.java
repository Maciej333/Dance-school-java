package mr.danceschool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.EmployeeDirector;

import java.util.List;

public interface EmployeeDirectorRepository extends JpaRepository<EmployeeDirector, Long> {

    List<EmployeeDirector> findAll();
}
