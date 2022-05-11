package ru.mirea.linguaschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public List<Review> findAllByTeacher(Teacher teacher);

    public List<Review> findAllByAuthor(User author);
}
