package ru.mirea.linguaschool.service;

import ru.mirea.linguaschool.repository.StudentRepository;
import ru.mirea.linguaschool.repository.TeacherRepository;

public class LinguaSchoolService {
    private final TeacherRepository teacherRepo;
    private final StudentRepository studentRepo;

    public LinguaSchoolService(TeacherRepository teacherRepo,
                            StudentRepository studentRepo) {
        this.teacherRepo = teacherRepo;
        this.studentRepo = studentRepo;
    }
}
