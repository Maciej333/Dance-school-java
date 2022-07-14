package s18454.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s18454.diploma.dao.UserRepository;
import s18454.diploma.entity.User;
import s18454.diploma.utils.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user){
        try {
            return userRepository.save(user);
        }catch (Exception e){
            System.out.println("--- ERROR, cannot save USER | "+e.getMessage());
            return null;
        }
    }

    @Override
    public User findById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "lastname"));
    }

    @Override
    public Integer deleteById(long id) {
        userRepository.deleteById(id);
        return 1;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if(user != null){
            Collection<Role> authorities = user.getRoles().stream().toList();
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
        }else{
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }
}
