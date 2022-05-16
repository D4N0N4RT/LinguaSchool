package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.model.Language;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public TeacherController(TeacherService teacherService,UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @GetMapping()
    public String getTeachers(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        if (user == null)
            model.addAttribute("user", null);
        else
            model.addAttribute("user", user);
        return "teachers";
    }

    @GetMapping("/{language}")
    public String languageTeachers(Model model, @PathVariable Language language) {
        List<Teacher> teachers = teacherService.findAllTeachersByLanguage(language);
        model.addAttribute("teachers", teachers);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        if (user == null)
            model.addAttribute("user", null);
        else
            model.addAttribute("user", user);
        return "teachers";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(Model model, @PathVariable long id) {
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if(teacher.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(teacher.get());
    }

    @RequestMapping("/{id}/reviews")
    public String getReviewsByTeacher(Model model, @PathVariable long id) {
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty()) {
            model.addAttribute("teacherNotFound", "Отзывы на данного учителя в данный момент недлоступны");
            return "reviews";
        }
        List<Review> reviews = teacher.get().getReviews();
        model.addAttribute("reviews", reviews);
        return "reviews";
    }
}