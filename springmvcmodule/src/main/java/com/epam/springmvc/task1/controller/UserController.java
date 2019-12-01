package com.epam.springmvc.task1.controller;

import com.epam.springmvc.task1.domain.User;
import com.epam.springmvc.task1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(Locale locale) {
        return new ModelAndView("login", "user", new User());
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration(Locale locale) {
        return new ModelAndView("registration", "user", new User());
    }

    @PostMapping(value = "/registration")
    public String registration(@Valid User user, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (userService.add(user)) {
            Cookie cookie = new Cookie("userLogin", user.getLogin());
            response.addCookie(cookie);
            return "redirect:/login";
        } else {
            result.addError(new FieldError("user", "login", "login already exist choose another"));
            return "/registration";
        }
    }

    @PostMapping(value = "/login")
    public String login(@Valid @ModelAttribute User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        if (!userService.isExist(user)) {
            result.addError(new FieldError("user", "login", "incorrect login"));
            return "login";
        } else {
            User existUser = userService.findUserByLogin(user.getLogin());
            session.setAttribute("user", existUser);
            return "redirect:/purchase";
        }
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        return "redirect:/login";
    }

}
