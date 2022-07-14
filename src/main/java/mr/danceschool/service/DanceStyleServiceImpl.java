package mr.danceschool.service;

import mr.danceschool.dao.DanceStyleRepository;
import mr.danceschool.entity.DanceStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanceStyleServiceImpl implements DanceStyleService {

    private DanceStyleRepository danceStyleRepository;

    @Autowired
    public DanceStyleServiceImpl(DanceStyleRepository danceStyleRepository) {
        this.danceStyleRepository = danceStyleRepository;
    }

    @Override
    public DanceStyle save(DanceStyle danceStyle) {
        return danceStyleRepository.save(danceStyle);
    }

    @Override
    public DanceStyle findById(long id) {
        Optional<DanceStyle> danceStyle = danceStyleRepository.findById(id);
        if(danceStyle.isPresent()){
            return danceStyle.get();
        }else{
            return null;
        }
    }

    @Override
    public List<DanceStyle> findAll() {
        return danceStyleRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public Integer deleteById(long id) {
        danceStyleRepository.deleteById(id);
        return 1;
    }

    @Override
    public DanceStyle findByName(String name) {
        return danceStyleRepository.findByName(name);
    }
}
