package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.ReviewService;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ReviewController {
    private ReviewService reviewService;
    private UserService userService;
    private TeacherService teacherService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, TeacherService teacherService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @PutMapping("/teachers/{id}/review")
    public String addReview(@PathVariable long id, @Valid Review review) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        review.setAuthor(user);
        review.setTeacher(teacher.get());
        reviewService.save(review);
        user.getReviews().add(review);
        teacher.get().getReviews().add(review);
        teacherService.saveTeacher(teacher.get());
        userService.saveUser(user);
        return "redirect:/teachers/" + id;
    }
}
