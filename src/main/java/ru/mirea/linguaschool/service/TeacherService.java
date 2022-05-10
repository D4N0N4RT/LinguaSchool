package ru.mirea.linguaschool.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.linguaschool.model.Language;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public long countTeachers() {
        return teacherRepository.count();
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public void saveTeacher(Teacher teacher) {
        if (teacher == null) {
            System.err.println("Teacher is null");
            return;
        }
        teacherRepository.save(teacher);
    }

    public Optional<Teacher> findTeacherById(long id) {
        return teacherRepository.findById(id);
    }

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public List<Teacher> findAllTeachersByLanguage(Language language) {
        return teacherRepository.findAllByLanguage(language);
    }
}
