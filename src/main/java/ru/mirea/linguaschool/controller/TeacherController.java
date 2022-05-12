package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.model.Language;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public TeacherController(TeacherService teacherService,UserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @RequestMapping("/{language}")
    public String languageTeachers(Model model, @PathVariable Language language) {
        List<Teacher> teachers = teacherService.findAllTeachersByLanguage(language);
        model.addAttribute("teachers", teachers);
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