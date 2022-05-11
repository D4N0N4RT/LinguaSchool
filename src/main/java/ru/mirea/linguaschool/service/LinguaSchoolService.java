package ru.mirea.linguaschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.linguaschool.repository.TeacherRepository;

@Service
public class LinguaSchoolService {
    private final TeacherRepository teacherRepo;

    @Autowired
    public LinguaSchoolService(TeacherRepository teacherRepo) {
        this.teacherRepo = teacherRepo;
    }
}
