package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.model.Language;
import ru.mirea.linguaschool.model.Teacher;
import ru.mirea.linguaschool.service.TeacherService;

import java.util.List;

@RestController
public class MainController {
    private TeacherService teacherService;

    @Autowired
    public MainController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/{language}")
    public String languageTeachers(Model model, @PathVariable Language language) {
        List<Teacher> teachers = teacherService.findAllTeachersByLanguage(language);
        model.addAttribute("teachers", teachers);
        return "teachers";
    }


}
