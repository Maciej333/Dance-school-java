package mr.danceschool.service;

import mr.danceschool.dao.StudentDanceStyleRepository;
import mr.danceschool.entity.StudentDanceStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentDanceStyleServiceImpl implements StudentDanceStyleService {

    private StudentDanceStyleRepository studentDanceStyleRepository;

    @Autowired
    public StudentDanceStyleServiceImpl(StudentDanceStyleRepository studentDanceStyleRepository) {
        this.studentDanceStyleRepository = studentDanceStyleRepository;
    }

    @Override
    public StudentDanceStyle save(StudentDanceStyle studentDanceStyle) {
        return studentDanceStyleRepository.save(studentDanceStyle);
    }

    @Override
    public StudentDanceStyle findById(long id) {
        Optional<StudentDanceStyle> studentDanceStyle = studentDanceStyleRepository.findById(id);
        if(studentDanceStyle.isPresent()){
            return studentDanceStyle.get();
        }else{
            return null;
        }
    }

    @Override
    public List<StudentDanceStyle> findAll() {
        return studentDanceStyleRepository.findAll();
    }

    @Override
    public Integer deleteById(long id) {
        studentDanceStyleRepository.deleteById(id);
        return 1;
    }
}
