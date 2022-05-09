package ru.mirea.linguaschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        //Если пароль у пользователя существует и не равен паролю для подтверждения,
        //то добавляем в модель ошибку
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordEqualsError", "Пароли не совпадают");
            return "registration";
        }

        //Получаем пользователя из БД передавая имя нового пользователя
        User userFromDB = userService.findByUsername(user.getUsername());
        //Если в бд такой пользователь существует, то выдаем сообщение об ошибке
        if (userFromDB != null) {
            model.addAttribute("userExistException", "Пользователь с таким ником уже существует");
            return "registration";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }
}
