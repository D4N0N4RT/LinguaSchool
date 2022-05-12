package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.linguaschool.service.TeacherService;

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


}
