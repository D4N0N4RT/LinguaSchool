package ru.mirea.linguaschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByTeacher(Teacher teacher);

    List<Review> findAllByAuthor(User author);
}
