package s18454.diploma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s18454.diploma.dao.StudentRepository;
import s18454.diploma.entity.Student;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findById(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }else{
            return null;
        }
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll().stream().sorted(Comparator.comparing((Student stu) -> stu.getUser().getLastname().toLowerCase())).toList();
    }

    @Override
    public Integer deleteById(long id) {
        studentRepository.deleteById(id);
        return 1;
    }
}
