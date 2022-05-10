package ru.mirea.linguaschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.linguaschool.model.Student;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void saveStudent(Student student) {
        if (student == null) {
            System.err.println("Student is null");
            return;
        }
        studentRepository.save(student);
    }

    public Optional<Student> findStudentById(long id) {
        return studentRepository.findById(id);
    }

    public long countStudents() {
        return studentRepository.count();
    }

    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
