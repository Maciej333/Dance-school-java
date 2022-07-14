package mr.danceschool.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import mr.danceschool.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    List<User> findAll(Sort sort);
}
