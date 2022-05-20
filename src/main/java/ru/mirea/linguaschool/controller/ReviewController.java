package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.ReviewService;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
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

    @RequestMapping("/reviews")
    public String reviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @RequestMapping("review/{id}")
    public String review(@PathVariable long id, Model model) {
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        model.addAttribute("teacher", teacher.get());
        return "review";
    }

    @PostMapping("/review/{id}")
    public String addReview(@PathVariable long id, @Valid Review review,
                            @RequestParam(name = "recommendation", defaultValue = "yes") String bool) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        Review review1 = reviewService.findAll().stream().filter(review2 -> review2.getTeacher() == teacher.get())
                .filter(review2 -> review2.getAuthor() == user).findFirst().orElse(null);
        boolean recommendation = Boolean.parseBoolean(Objects.equals(bool, "yes") ? "true" : "false");
        if (review1 == null) {
            review1 = new Review();
            review1.setAuthor(user);
            review1.setTeacher(teacher.get());
            review1.setRecommended(recommendation);
            review1.setText(review.getText());
            reviewService.save(review1);
        }
        review1.setRecommended(recommendation);
        review1.setText(review.getText());
        return "redirect:/teachers";
    }
}
