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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.linguaschool.model.Review;
import ru.mirea.linguaschool.model.Role;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.ReviewService;
import ru.mirea.linguaschool.service.TeacherService;
import ru.mirea.linguaschool.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private TeacherService teacherService;
    private ReviewService reviewService;

    @Autowired
    public AdminController(UserService userService, TeacherService teacherService, ReviewService reviewService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.reviewService = reviewService;
    }

    @GetMapping("/teachers")
    public String adminTeachers(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        model.addAttribute("user", user);
        Set<Role> roles = user.getRoles();
        model.addAttribute("role", roles.contains(Role.ADMIN));
        return "teachers";
    }

    @GetMapping("/teacher")
    public String getTeacher() {
        return "teacher";
    }

    @PostMapping("/teacher")
    public String addTeacher(@Valid Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/del/{id}")
    public ResponseEntity<?> fireTeacher(@PathVariable long id) {
        Optional<Teacher> teacher = teacherService.findTeacherById(id);
        if (teacher.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for (User user : teacher.get().getStudents()){
            user.setTeacher(null);
            userService.saveUser(user);
        }
        for (Review review : teacher.get().getReviews()){
            reviewService.delete(review);
        }
        teacherService.deleteTeacher(teacher.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
