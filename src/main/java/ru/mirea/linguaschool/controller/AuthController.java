package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mirea.linguaschool.model.User;
import ru.mirea.linguaschool.service.UserService;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, Model model) {

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordEqualsError", "Пароли не совпадают");
            return "registration";
        }

        User userFromDB = userService.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("userExistError", "Пользователь с таким ником уже существует");
            return "registration";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }
}
