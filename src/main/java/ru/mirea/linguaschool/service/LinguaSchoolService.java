package ru.mirea.linguaschool.service;

import ru.mirea.linguaschool.model.Student;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.repository.StudentRepository;
import ru.mirea.linguaschool.repository.TeacherRepository;

import java.util.List;

public class LinguaSchoolService {
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    public LinguaSchoolService(TeacherRepository teacherRepo,
                            StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }

    public long countTeachers() {
        return teacherRepo.count();
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepo.delete(teacher);
    }

    public void saveTeacher(Teacher teacher) {
        if (teacher == null) {
            System.err.println("Teacher is null");
            return;
        }
        teacherRepo.save(teacher);
    }

    public long countStudents() {
        return studentRepo.count();
    }

    public void deleteStudent(Student student) {
        studentRepo.delete(student);
    }

    public void saveStudent(Student student) {
        if (student == null) {
            System.err.println("Student is null");
            return;
        }
        studentRepo.save(student);
    }

    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepo.findAll();
    }
}
