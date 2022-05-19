package ru.mirea.linguaschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<Review> findAllByTeacher(Teacher teacher) {
        return reviewRepository.findAllByTeacher(teacher);
    }

    public List<Review> findAllByAuthor(User author) {
        return reviewRepository.findAllByAuthor(author);
    }
}
