package s18454.diploma.service;

import s18454.diploma.entity.User;

import java.util.List;

public interface UserService {

    public User save(User user);

    public User findById(long id);

    public List<User> findAll();

    public Integer deleteById(long id);

    public User findByLogin(String login);

}
