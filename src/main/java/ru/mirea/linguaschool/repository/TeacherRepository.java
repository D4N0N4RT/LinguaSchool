package ru.mirea.linguaschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.linguaschool.model.Language;
import ru.mirea.linguaschool.model.Teacher;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAllByName(String name);

    List<Teacher> findAllBySurname(String surname);

    List<Teacher> findAllByLanguage(Language language);

    List<Teacher> findAllByWorkExperience(int workExperience);
}
