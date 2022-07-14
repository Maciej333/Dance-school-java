package s18454.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s18454.diploma.dao.PassRepository;
import s18454.diploma.entity.Pass;

import java.util.List;
import java.util.Optional;

@Service
public class PassServiceImpl implements PassService {

    private PassRepository passRepository;

    @Autowired
    public PassServiceImpl(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    @Override
    public Pass save(Pass pass) {
        return passRepository.save(pass);
    }

    @Override
    public Pass findById(long id) {
        Optional<Pass> pass = passRepository.findById(id);
        if(pass.isPresent()){
            return pass.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Pass> findAll() {
        return passRepository.findAll();
    }

    @Override
    public Integer deleteById(long id) {
        passRepository.deleteById(id);
        return 1;
    }
}
